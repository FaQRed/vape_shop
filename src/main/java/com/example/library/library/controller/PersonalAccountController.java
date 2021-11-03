package com.example.library.library.controller;

import com.example.library.library.model.user.User;
import com.example.library.library.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/personalCabinet")
public class PersonalAccountController {

    UserService userService;

    private static final String EDIT_PROFILE = "userWebSite/personalAccount";

    public PersonalAccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/edit")
    public String editUser(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> user = userService.getUserByLogin(auth.getName());
            model.addAttribute("user", user);
            return EDIT_PROFILE;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return EDIT_PROFILE;
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOrig = userService.getUserByLogin(auth.getName());
        try {
            if (userOrig.isPresent()) {
                if (userOrig.get().getLogin() == null || user.getLogin().equals(userOrig.get().getLogin())) {
                    user.setLogin(userOrig.get().getLogin());
                }
                if (user.getStatus() == null) {
                    user.setStatus(userOrig.get().getStatus());
                }
                user.setRoles(userOrig.get().getRoles());
            }

            userService.updateUser(user);
            model.addAttribute("message", "Пользователь успешно изменен");
            model.addAttribute("alertClass", "alert-success");
            return EDIT_PROFILE;
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка редактирования пользователя");
            model.addAttribute("alertClass", "alert-danger");
            return EDIT_PROFILE;
        }
    }
}
