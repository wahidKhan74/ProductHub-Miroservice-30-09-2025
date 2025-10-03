package com.webservice.auth_service.repository;

import com.webservice.auth_service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

  Optional<Users> findByUsername(String username);

  // Find users by email and order them by email (default ordering, usually ascending)
  List<Users> findByEmailOrderByEmail(String email);

  // Find users by email and order them explicitly in ascending order
  List<Users> findByEmailOrderByEmailAsc(String email);

  List<Users>  findTop5ByEmailOrderByEmailAsc(String email);
}
