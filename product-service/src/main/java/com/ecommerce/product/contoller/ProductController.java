package com.ecommerce.product.contoller;

import com.ecommerce.product.dto.Product;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.service.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<ProductResponse> getAllProducts() {
        LOGGER.info("Request received to getAllProducts.");
        List<com.ecommerce.product.entity.Product> allProducts = productService.getAllProducts();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(mapEntityToDto(allProducts));
        LOGGER.info("Returning response of getAllProducts");
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        LOGGER.info("Request received to add product");
        com.ecommerce.product.entity.Product savedCategory = productService.saveProduct(mapDtoToEntity(product));
        LOGGER.info("Returning response of add product");
        return ResponseEntity.ok(mapEntityToDto(savedCategory));
    }

    private List<Product> mapEntityToDto(List<com.ecommerce.product.entity.Product> productsEntity) {
        return productsEntity.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    private Product mapEntityToDto(com.ecommerce.product.entity.Product product) {
        return new ObjectMapper().convertValue(product, Product.class);
    }

    private com.ecommerce.product.entity.Product mapDtoToEntity(Product product) {
        return new ObjectMapper().convertValue(product, com.ecommerce.product.entity.Product.class);
    }
}
