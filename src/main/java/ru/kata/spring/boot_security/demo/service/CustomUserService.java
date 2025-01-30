package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface CustomUserService extends UserDetailsService {
    List<User> allUsers();

    User findByUsername(String username);

    void saveUser(User user);
    void deleteUser(Long userId);
    User findById(Long id);
    Set<Role> getRoles(User user);
    void updateUser(User user);
}
