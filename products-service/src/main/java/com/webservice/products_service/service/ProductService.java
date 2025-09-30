package com.webservice.products_service.service;

import com.webservice.products_service.dto.Response;
import com.webservice.products_service.entity.Product;
import com.webservice.products_service.exception.BadRequest;
import com.webservice.products_service.exception.NotFound;
import com.webservice.products_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  public ResponseEntity<Response> getAll() {
    List<Product> list = productRepository.findAll();
    return new ResponseEntity<>(
      new Response("Products are found successfully", list),
      HttpStatus.OK
    );
  }

  public ResponseEntity<Response> save(Product product) {
    try {
      Product savedProduct  = productRepository.save(product);
      return new ResponseEntity<>(
        new Response("Product is created successfully", savedProduct),
        HttpStatus.CREATED
      );
    } catch (Exception ex) {
      throw new BadRequest("Product details are not correct");
    }
  }

  public ResponseEntity<Response> update(Product product) {
    Optional<Product> fetchProduct = productRepository.findById(product.getId());
    if(fetchProduct.isPresent()) {
      Product updatedProduct = productRepository.save(product);
      return new ResponseEntity<>(
        new Response("Product is updated successfully", updatedProduct),
        HttpStatus.OK
      );
    } else{
      throw new NotFound("Product not found: " + product.getId());
    }
  }

  public ResponseEntity<Response> delete(String id) {
    Optional<Product> product = productRepository.findById(id);
    if(product.isPresent()) {
      productRepository.deleteById(id);
      return new ResponseEntity<>(new Response("Product is deleted successfully : " + id), HttpStatus.OK);
    } else{
      throw new NotFound("Product not found: " + id);
    }
  }

  public ResponseEntity<Response> getById(String id) {
    Optional<Product> product = productRepository.findById(id);
    if(product.isPresent()) {
      return new ResponseEntity<>(new Response("Product is found successfully", product.get()), HttpStatus.OK);
    } else{
      throw new NotFound("Product not found: " + id);
    }
  }
}
