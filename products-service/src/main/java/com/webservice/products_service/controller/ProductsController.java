package com.webservice.products_service.controller;

import com.webservice.products_service.dto.Response;
import com.webservice.products_service.entity.Product;
import com.webservice.products_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductsController {

  @Autowired
  private ProductService productService;

  // GET all products : Fetch all products data
  @GetMapping()
  public ResponseEntity<Response> getProducts() {
    return productService.getAll();
  }

  // Get one product by id
  @GetMapping("/{id}")
  public ResponseEntity<Response> getProduct(@PathVariable String id) {
    return productService.getById(id);
  }

  // Add new product
  @PostMapping()

  public ResponseEntity<Response> addProduct(@RequestBody Product product) {
    return productService.save(product);
  }

  // Update a product
  @PutMapping("/{id}")
  public ResponseEntity<Response> updateProduct(@PathVariable String id, @RequestBody Product product) {
    product.setId(id);
    return productService.update(product);
  }

  // Delete a product
  @DeleteMapping("/{id}")
  public ResponseEntity<Response> deleteProduct(@PathVariable String id) {
    return productService.delete(id);
  }

}
