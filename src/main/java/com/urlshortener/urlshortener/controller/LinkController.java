package com.urlshortener.urlshortener.controller;

import com.urlshortener.urlshortener.dto.*;
import com.urlshortener.urlshortener.entity.Link;
import com.urlshortener.urlshortener.service.LinkService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class LinkController {

    private final LinkService service;

    public LinkController(LinkService service) {
        this.service = service;
    }

    private String obterOuCriarUserId(HttpServletRequest request,
                                      HttpServletResponse response) {

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("userId")) {
                    return cookie.getValue();
                }
            }
        }

        String novoUserId = UUID.randomUUID().toString();

        Cookie cookie = new Cookie("userId", novoUserId);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 365);

        response.addCookie(cookie);

        return novoUserId;
    }

    @PostMapping("/shorten")
    @ResponseBody
    public ResponseEntity<ShortenResponse> encurtar(
            @RequestBody ShortenRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse response) {

        String userId = obterOuCriarUserId(httpRequest, response);

        Link link = service.encurtar(request.getUrl(), userId);

        String baseUrl = httpRequest.getRequestURL().toString()
                .replace("/shorten", "");

        String shortUrl = baseUrl + "/" + link.getShortCode();

        return ResponseEntity.ok(new ShortenResponse(shortUrl));
    }

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

    @GetMapping("/links")
    @ResponseBody
    public ResponseEntity<List<LinkResponse>> listar(
            HttpServletRequest request,
            HttpServletResponse response) {

        String userId = obterOuCriarUserId(request, response);

        List<LinkResponse> links = service.listarPorUsuario(userId)
                .stream()
                .map(LinkResponse::new)
                .toList();

        return ResponseEntity.ok(links);
    }

    @GetMapping("/ranking")
    @ResponseBody
    public ResponseEntity<List<LinkResponse>> ranking(
            HttpServletRequest request,
            HttpServletResponse response) {

        String userId = obterOuCriarUserId(request, response);

        List<LinkResponse> ranking = service.rankingTop5(userId)
                .stream()
                .map(LinkResponse::new)
                .toList();

        return ResponseEntity.ok(ranking);
    }

    @PostMapping("/shorten-ui")
    public String encurtarUI(@RequestParam String url,
                             Model model,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        String userId = obterOuCriarUserId(request, response);

        Link link = service.encurtar(url, userId);

        String baseUrl = request.getRequestURL().toString();

        String shortUrl = baseUrl.replace("/shorten-ui", "")
                + "/" + link.getShortCode();

        model.addAttribute("shortUrl", shortUrl);

        return "result";
    }
}