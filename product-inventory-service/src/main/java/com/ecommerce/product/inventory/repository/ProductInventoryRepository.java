package com.ecommerce.product.inventory.repository;

import com.ecommerce.product.inventory.entity.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {
}
