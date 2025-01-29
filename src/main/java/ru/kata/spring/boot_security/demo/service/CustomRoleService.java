package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface CustomRoleService {
    List<Role> findAll();

    Set<Role> getSetRoles(String[] roleNames);

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role getRoleByName(@Param("name") String name);
}
