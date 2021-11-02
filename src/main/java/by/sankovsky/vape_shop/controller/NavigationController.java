package com.example.library.library.controller;

import com.example.library.library.service.PodService;
import com.example.library.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NavigationController {

    private UserService userService;
    private PodService podService;

    @Autowired
    public NavigationController(UserService userService, PodService podService) {
        this.podService = podService;
        this.userService = userService;
    }

    @RequestMapping(value = {"/", "/users"}, method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/user";
    }

    @GetMapping("/references")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public String references() {
        return "references/references";
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public String statistics() {
        return "statistics";
    } //Приходят заказанные товары

    @GetMapping("/goods")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public String books(Model model){
        model.addAttribute("books", podService.getAllPods());
    return "references/userPage/library";
    }
}