package com.urlshortener.urlshortener.controller;

import com.urlshortener.urlshortener.dto.*;
import com.urlshortener.urlshortener.entity.Link;
import com.urlshortener.urlshortener.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
public class LinkController {

    private final LinkService service;

    public LinkController(LinkService service) {
        this.service = service;
    }

    // 1 - criar link (API JSON)
    @PostMapping("/shorten")
    @ResponseBody
    public ResponseEntity<ShortenResponse> encurtar(@RequestBody ShortenRequest request) {

        Link link = service.encurtar(request.getUrl());

        String shortUrl = "http://localhost:8080/" + link.getShortCode();

        return ResponseEntity.ok(new ShortenResponse(shortUrl));
    }

    // 2 - redirecionar + contar clicks
    @GetMapping("/{code}")
    public ResponseEntity<Void> redirecionar(@PathVariable String code) {

        Optional<Link> linkOpt = service.buscar(code);

        if (linkOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Link link = linkOpt.get();

        service.incrementarClicks(code);

        return ResponseEntity.status(302)
                .location(URI.create(link.getOriginalUrl()))
                .build();
    }

    // 3 - stats
    @GetMapping("/stats/{code}")
    @ResponseBody
    public ResponseEntity<LinkStatsResponse> stats(@PathVariable String code) {

        Optional<Link> linkOpt = service.buscar(code);

        if (linkOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Link link = linkOpt.get();

        return ResponseEntity.ok(
                new LinkStatsResponse(
                        link.getOriginalUrl(),
                        link.getShortCode(),
                        link.getClicks()
                )
        );
    }

    // 4 - listar todos
    @GetMapping("/links")
    @ResponseBody
    public ResponseEntity<List<Link>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // 5 - ranking
    @GetMapping("/ranking")
    @ResponseBody
    public ResponseEntity<List<Link>> ranking() {
        return ResponseEntity.ok(service.rankingTop5());
    }

    // 6 - UI formulário (CORRIGIDO PARA DEPLOY)
    @PostMapping("/shorten-ui")
    public String encurtarUI(@RequestParam String url,
                             Model model,
                             HttpServletRequest request) {

        Link link = service.encurtar(url);

        // 🔥 URL dinâmica (funciona local + render)
        String baseUrl = request.getRequestURL().toString();
        String shortUrl = baseUrl.replace("/shorten-ui", "")
                + "/" + link.getShortCode();

        model.addAttribute("shortUrl", shortUrl);

        return "result";
    }
}