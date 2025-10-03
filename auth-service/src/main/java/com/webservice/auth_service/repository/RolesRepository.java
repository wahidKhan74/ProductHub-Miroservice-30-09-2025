package com.webservice.auth_service.repository;

import com.webservice.auth_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role, String> {
}
