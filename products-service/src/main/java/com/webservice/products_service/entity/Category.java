package com.webservice.products_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
  private String subCategory;        // Sub-category (e.g., mobile, shoes)

  @ManyToMany( mappedBy = "categories")
  @JsonIgnore
  private List<Product> products;
}
