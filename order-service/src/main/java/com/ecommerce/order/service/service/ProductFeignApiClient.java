package com.ecommerce.order.service.service;

import com.ecommerce.order.service.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-INVENTORY-SERVICE")
public interface ProductFeignApiClient {

    @GetMapping("/products/inventory/{id}")
    ProductResponse getProduct(@PathVariable("id") long id);
}
