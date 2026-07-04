package com.urlshortener.urlshortener.controller;

import com.urlshortener.urlshortener.service.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final LinkService service;

    public DashboardController(LinkService service) {
        this.service = service;
    }

    @GetMapping("/dashboard")
    public String dashboard(
            Model model,
            @CookieValue(value = "userId", required = false) String userId) {

        if (userId == null || userId.isEmpty()) {
            userId = "guest";
        }

        model.addAttribute("links", service.listarPorUsuario(userId));
        model.addAttribute("ranking", service.rankingTop5(userId));

        return "dashboard";
    }
}