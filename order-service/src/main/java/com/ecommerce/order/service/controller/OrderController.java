package com.ecommerce.order.service.controller;

import com.ecommerce.order.service.dto.OrderQuotationRequest;
import com.ecommerce.order.service.dto.OrderQuotationResponse;
import com.ecommerce.order.service.dto.ProductResponse;
import com.ecommerce.order.service.dto.QuotationResponse;
import com.ecommerce.order.service.entity.Order;
import com.ecommerce.order.service.exception.ProductException;
import com.ecommerce.order.service.service.OrderService;
import com.ecommerce.order.service.service.ProductFeignApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductFeignApiClient productFeignApiClient;

    @PostMapping(value = "/quotation", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrderQuotationResponse> requestQuotation(@RequestBody OrderQuotationRequest orderQuotationRequest) {
        LOGGER.info("Request received to request quotation");

        long productId = orderQuotationRequest.getProductId();

        // fetch product from product-inventory-service
        ProductResponse productResponse = fetchProduct(productId);
        if (!productResponse.isInStock()) {
            throw new ProductException(String.format("Product %s in currently out of stock", productResponse.getName()));
        }

        Order quotation = orderService.saveOrder(prepareOrderQuotation(orderQuotationRequest, productResponse));

        OrderQuotationResponse orderQuotationResponse = new OrderQuotationResponse();
        orderQuotationResponse.setId(quotation.getId());
        orderQuotationResponse.setProductId(quotation.getProductId());
        orderQuotationResponse.setProductName(quotation.getProductName());

        QuotationResponse quotationResponse = new QuotationResponse();
        quotationResponse.setQuantity(quotation.getQuantity());
        quotationResponse.setUnitPrice(quotation.getUnitPrice());
        quotationResponse.setDiscount(quotation.getDiscount());
        quotationResponse.setTotalPrice(quotation.getTotalPrice());
        quotationResponse.setFinalPrice(quotation.getFinalPrice());
        orderQuotationResponse.setQuotationResponse(quotationResponse);

//        QuotationResponse quotationResponse = new ObjectMapper().convertValue(quotation, QuotationResponse.class);

        LOGGER.info("Returning response for OrderQuotation");
        return ResponseEntity.ok(orderQuotationResponse);
    }

    private ProductResponse fetchProduct(long productId) {
        try {
            return productFeignApiClient.getProduct(productId);
        } catch (Exception e) {
            throw new ProductException(e.getMessage());
        }
    }

    private Order prepareOrderQuotation(OrderQuotationRequest orderQuotationRequest, ProductResponse productResponse) {
        Order order = new Order();
        order.setProductId(productResponse.getId());
        order.setProductName(productResponse.getName());
        order.setUnitPrice(productResponse.getUnitPrice());
        order.setQuantity(orderQuotationRequest.getQuantity());

        float discountForUnit = productResponse.getUnitPrice() * (productResponse.getDiscountInPercentage() / 100);
        float unitPriceAfterDiscount = productResponse.getUnitPrice() - discountForUnit;
        float finalPrice = unitPriceAfterDiscount * orderQuotationRequest.getQuantity();

        order.setDiscount(discountForUnit * orderQuotationRequest.getQuantity());
        order.setTotalPrice(productResponse.getUnitPrice() * orderQuotationRequest.getQuantity());
        order.setFinalPrice(finalPrice);
        order.setState("QUOTATION_SENT");
        return order;
    }

}
