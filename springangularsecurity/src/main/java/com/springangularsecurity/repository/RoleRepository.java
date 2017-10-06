package com.springangularsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springangularsecurity.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
