package com.webservice.orders_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private int quantity;
  private double price;

  // Many items -> one order
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
}
