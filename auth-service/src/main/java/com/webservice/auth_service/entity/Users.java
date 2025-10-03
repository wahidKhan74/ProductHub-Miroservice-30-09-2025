package com.webservice.auth_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Table(name="users")
@Entity
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(unique = true, nullable = false)
  private String username;
  private String password;
  private String phone;
  private String email;
  private boolean enable;

  @ManyToMany(fetch = FetchType.EAGER )
  @JoinTable(
    name = "users_roles", joinColumns = @JoinColumn(name = "users_id"), // FK product
    inverseJoinColumns = @JoinColumn(name = "roles_id") // FK category
  )
  private Set<Role> roles = new HashSet<Role>();

}
