package com.ecommerce.product.inventory.contoller;

import com.ecommerce.product.inventory.dto.ProductInventory;
import com.ecommerce.product.inventory.exception.ProductNotFoundException;
import com.ecommerce.product.inventory.service.ProductInventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products/inventory")
public class ProductInventoryController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductInventoryController.class);

    @Autowired
    private ProductInventoryService productInventoryService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProductInventory>> getAllProducts() {
        LOGGER.info("Request received to getAllProducts.");
        List<com.ecommerce.product.inventory.entity.ProductInventory> allProductInventories = productInventoryService.getAllProducts();
        LOGGER.info("Returning response of getAllProducts");
        return ResponseEntity.ok(mapEntityToDto(allProductInventories));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ProductInventory> addProduct(@Valid @RequestBody ProductInventory productInventory) {
        LOGGER.info("Request received to add product");
        com.ecommerce.product.inventory.entity.ProductInventory savedProduct = productInventoryService.saveProduct(mapDtoToEntity(productInventory));
        LOGGER.info("Returning response of add product");
        return ResponseEntity.ok(mapEntityToDto(savedProduct));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductInventory> findProduct(@PathVariable(name = "id") long id) {
        LOGGER.info("Request received to find product by id");
        com.ecommerce.product.inventory.entity.ProductInventory product = productInventoryService.findProduct(id);
        if (null == product) {
            throw new ProductNotFoundException(String.format("Product %d is not found", id));
        }
        LOGGER.info("Returning response of find product by id");
        return ResponseEntity.ok(mapEntityToDto(product));
    }

    private List<ProductInventory> mapEntityToDto(List<com.ecommerce.product.inventory.entity.ProductInventory> productsEntity) {
        return productsEntity.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    private ProductInventory mapEntityToDto(com.ecommerce.product.inventory.entity.ProductInventory productInventory) {
        return new ObjectMapper().convertValue(productInventory, ProductInventory.class);
    }

    private com.ecommerce.product.inventory.entity.ProductInventory mapDtoToEntity(ProductInventory productInventory) {
        return new ObjectMapper().convertValue(productInventory, com.ecommerce.product.inventory.entity.ProductInventory.class);
    }
}
