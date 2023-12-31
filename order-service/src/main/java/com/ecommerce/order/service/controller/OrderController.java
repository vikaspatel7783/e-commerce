package com.ecommerce.order.service.controller;

import com.ecommerce.order.service.dto.*;
import com.ecommerce.order.service.entity.Order;
import com.ecommerce.order.service.exception.CheckoutException;
import com.ecommerce.order.service.exception.OrderException;
import com.ecommerce.order.service.exception.ProductException;
import com.ecommerce.order.service.producer.MessageProducer;
import com.ecommerce.order.service.producer.OrderEvent;
import com.ecommerce.order.service.service.CheckoutFeignApiClient;
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

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    public static final String QUOTATION_SENT = "QUOTATION_SENT";
    public static final String CONFIRMED = "CONFIRMED";

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductFeignApiClient productFeignApiClient;
    @Autowired
    private CheckoutFeignApiClient checkoutFeignApiClient;
    @Autowired
    private MessageProducer messageProducer;

    @PostMapping(value = "/quotation", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrderQuotationResponse> requestQuotation(@RequestBody OrderQuotationRequest orderQuotationRequest) {
        LOGGER.info("Request received to request quotation");

        long productId = orderQuotationRequest.getProductId();

        // fetch product from product-inventory-service
        ProductResponse productResponse = invokeFetchProductService(productId);
        if (!productResponse.isInStock()) {
            throw new ProductException(String.format("Product %s in currently out of stock", productResponse.getName()));
        }

        Order quotation = orderService.saveOrder(prepareOrderQuotation(orderQuotationRequest, productResponse));
        OrderQuotationResponse orderQuotationResponse = prepareOrderQuotationResponse(quotation);
        OrderEvent orderEvent = new OrderEvent(quotation);
        messageProducer.sendOrderMessage(orderEvent);

        LOGGER.info("Returning response for OrderQuotation");
        return ResponseEntity.ok(orderQuotationResponse);
    }

    @PostMapping(value = "/confirm", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ConfirmOrderResponse> confirmOrder(@RequestBody ConfirmOrderRequest confirmOrderRequest) {
        LOGGER.info("Request received to confirm order: {}", confirmOrderRequest.getOrderId());

        Order order = getSavedOrder(confirmOrderRequest);

        publishOrderInitiatedEvent(order);

        CheckoutResponse checkout = invokeCheckoutService(order);

        publishOrderConfirmedEvent(order, checkout);

        order.setState(CONFIRMED);
        order.setCheckoutId(checkout.getCheckoutId());
        orderService.saveOrder(order);

        ConfirmOrderResponse confirmOrderResponse = prepareConfirmOrderResponse(order, checkout);

        LOGGER.info("Returning response for ConfirmOrder");
        return ResponseEntity.ok(confirmOrderResponse);
    }

    private static ConfirmOrderResponse prepareConfirmOrderResponse(Order order, CheckoutResponse checkout) {
        ConfirmOrderResponse confirmOrderResponse = new ConfirmOrderResponse();
        confirmOrderResponse.setOrderId(order.getId());
        confirmOrderResponse.setOrderState(CONFIRMED);
        confirmOrderResponse.setCheckoutId(checkout.getCheckoutId());
        return confirmOrderResponse;
    }

    private void publishOrderConfirmedEvent(Order order, CheckoutResponse checkout) {
        OrderEvent orderEvent = new OrderEvent(order);
        orderEvent.getOrder().setState("CONFIRMED");
        orderEvent.setCheckoutId(checkout.getCheckoutId());
        messageProducer.sendOrderMessage(orderEvent);
    }

    private void publishOrderInitiatedEvent(Order order) {
        OrderEvent orderEvent = new OrderEvent(order);
        orderEvent.getOrder().setState("INITIATED");
        messageProducer.sendOrderMessage(orderEvent);
    }

    private Order getSavedOrder(ConfirmOrderRequest confirmOrderRequest) {
        long orderId = confirmOrderRequest.getOrderId();
        Order order = orderService.getOrder(orderId).orElseThrow(() -> new OrderException("Order not found: "+orderId));
        if (!order.getState().equals(QUOTATION_SENT)) {
            throw new OrderException("Order quotation not found for: "+orderId);
        }
        return order;
    }

    private OrderQuotationResponse prepareOrderQuotationResponse(Order quotation) {
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
        return orderQuotationResponse;
    }

    private ProductResponse invokeFetchProductService(long productId) {
        try {
            return productFeignApiClient.getProduct(productId);
        } catch (Exception e) {
            throw new ProductException(e.getMessage());
        }
    }

    private CheckoutResponse invokeCheckoutService(Order order) {
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setOrderId(String.valueOf(order.getId()));
        try {
            return checkoutFeignApiClient.checkout(checkoutRequest);
        } catch (Exception e) {
            throw new CheckoutException(e.getMessage());
        }
    }

    private Order prepareOrderQuotation(OrderQuotationRequest orderQuotationRequest, ProductResponse productResponse) {
        Order order = new Order();
        order.setProductId(productResponse.getId());
        order.setProductName(productResponse.getName());
        order.setUnitPrice(productResponse.getUnitPrice());
        order.setQuantity(orderQuotationRequest.getQuantity());

        float discountForUnit = productResponse.getUnitPrice() * (productResponse.getDiscountPercent() / 100);
        float unitPriceAfterDiscount = productResponse.getUnitPrice() - discountForUnit;
        float finalPrice = unitPriceAfterDiscount * orderQuotationRequest.getQuantity();

        order.setDiscount(discountForUnit * orderQuotationRequest.getQuantity());
        order.setTotalPrice(productResponse.getUnitPrice() * orderQuotationRequest.getQuantity());
        order.setFinalPrice(finalPrice);
        order.setState(QUOTATION_SENT);
        return order;
    }

}
