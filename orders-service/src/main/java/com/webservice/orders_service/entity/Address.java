package com.webservice.orders_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String street;
  private String city;
  private String state;
  private String country;
  private String postalCode;
  private boolean isDefault;
}

