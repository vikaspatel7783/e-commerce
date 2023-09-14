package com.ecommerce.product.inventory.service;

import com.ecommerce.product.inventory.entity.ProductInventory;

import java.util.List;

public interface ProductInventoryService {
    List<ProductInventory> getAllProducts();

    ProductInventory saveProduct(ProductInventory productInventory);

    ProductInventory findProduct(long id);
}
