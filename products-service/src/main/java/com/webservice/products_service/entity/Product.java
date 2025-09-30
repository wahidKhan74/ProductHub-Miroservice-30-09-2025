package com.webservice.products_service.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;                 // Unique product ID

  private String name;               // Product name
  private String description;        // Detailed description
  private double price;              // Base price
  private double discountPrice;      // Discounted price if applicable
  private boolean isAvailable;       // Availability status
  private int stock;                 // Quantity available
  private String sku;                // Stock Keeping Unit
  private String brand;              // Brand name

  @ManyToMany()
  @JoinTable(
    name = "product_category", joinColumns = @JoinColumn(name = "product_id"), // FK product
    inverseJoinColumns = @JoinColumn(name = "category_id") // FK category
  )
  private Set<Category> categories;     // Category (e.g., electronics, fashion)

  private String thumbnail;          // Main thumbnail image
  private double rating;             // Average customer rating
  private int totalReviews;          // Number of reviews
  private LocalDate createdAt;       // Creation timestamp
  private LocalDate updatedAt;       // Last update timestamp

  private boolean isFeatured;        // Highlighted product
  private boolean isOnSale;          // For promotional purposes

  @ElementCollection
  private List<String> tags;         // Search tags (SEO, keywords)
  @ElementCollection
  private List<String> images;       // URLs of product images

}
