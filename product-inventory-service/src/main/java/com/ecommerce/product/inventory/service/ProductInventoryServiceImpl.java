package com.ecommerce.product.inventory.service;

import com.ecommerce.product.inventory.entity.ProductInventory;
import com.ecommerce.product.inventory.repository.ProductInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

    @Autowired
    ProductInventoryRepository productInventoryRepository;

    public List<ProductInventory> getAllProducts() {
        return productInventoryRepository.findAll();
    }

    @Override
    public ProductInventory saveProduct(ProductInventory productInventory) {
        return productInventoryRepository.save(productInventory);
    }
}
