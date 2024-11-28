package com.sayedbaladoh.repository;

import com.sayedbaladoh.enums.RoleName;
import com.sayedbaladoh.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
