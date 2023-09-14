package com.ecommerce.order.service.controller;

import com.ecommerce.order.service.dto.OrderQuotationRequest;
import com.ecommerce.order.service.dto.OrderQuotationResponse;
import com.ecommerce.order.service.dto.ProductResponse;
import com.ecommerce.order.service.exception.ProductException;
import com.ecommerce.order.service.service.OrderService;
import com.ecommerce.order.service.service.ProductFeignApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

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

        LOGGER.info("Returning response for OrderQuotation");
        return ResponseEntity.ok(new OrderQuotationResponse());
    }

    private ProductResponse fetchProduct(long productId) {
        try {
            return productFeignApiClient.getProduct(productId);
        } catch (Exception e) {
            throw new ProductException(e.getMessage());
        }
    }

    private void mapToOrderQuotationResponse() {

    }
}
