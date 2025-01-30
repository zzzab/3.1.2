package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.CustomRoleService;
import ru.kata.spring.boot_security.demo.service.CustomUserService;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final CustomRoleService roleService;

    private final CustomUserService userService;


    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserDetails details, Model model) {
        User user = userService.findByUsername(details.getUsername());
        List<String> listRoles = details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // Extract role names
                .collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user";
    }

    @GetMapping("/admin")
    public String usersList(Model model) {
        List<User> list = userService.allUsers();
        model.addAttribute("users", list);
        return "users-list";
    }

    @GetMapping("/admin/edit/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        List<Role> listRoles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "edit-form";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "edit-form";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/add")
    public String addUserForm() {
        return "add-user-form";
    }

    @PostMapping("/add")
    public String addUser(User user,
                          @RequestParam(value = "rolesId") String[] roles) {
        user.setRoles(roleService.getSetRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
