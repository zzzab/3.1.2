package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.CustomRoleService;
import ru.kata.spring.boot_security.demo.service.CustomUserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final CustomRoleService roleService;

    private final CustomUserService userService;


    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserDetails details, Model model) {
        User user = userService.findByUsername(details.getUsername());
        model.addAttribute("user", user);
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
    public String updateUser(User user) {
        userService.saveUser(user);
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
