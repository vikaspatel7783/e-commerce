package com.ecommerce.product.inventory.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
@Getter
public class DiscountProviderService {

    @Value("${product.discount.percentage}")
    private float discountPercent;
}
