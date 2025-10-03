package com.webservice.auth_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name="roles")
@Entity
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;

}
