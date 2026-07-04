package com.urlshortener.urlshortener.controller;

import com.urlshortener.urlshortener.service.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final LinkService service;

    public DashboardController(LinkService service) {
        this.service = service;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("links", service.listarTodos());
        model.addAttribute("ranking", service.rankingTop5());

        return "dashboard";
    }
}