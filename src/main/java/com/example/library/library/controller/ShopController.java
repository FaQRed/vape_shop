package com.example.library.library.controller;

import com.example.library.library.model.goods.Pod;
import com.example.library.library.service.goods.pod.PodService;
import com.example.library.library.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    UserService userService;

    PodService podService;

    public ShopController(UserService userService, PodService podService) {
        this.userService = userService;
        this.podService = podService;
    }

    @GetMapping()
    public String getAll(Model model) {
        List<Pod> pods = podService.getAllPods();
        model.addAttribute("pods", pods);
        return "userWebSite/shop";
    }
}
