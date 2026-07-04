package com.urlshortener.urlshortener.service;

import com.urlshortener.urlshortener.entity.Link;
import com.urlshortener.urlshortener.repository.LinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LinkService {

    private final LinkRepository repository;

    public LinkService(LinkRepository repository) {
        this.repository = repository;
    }

    // criar link
    public Link encurtar(String originalUrl) {
        Link link = new Link();
        link.setOriginalUrl(originalUrl);
        link.setShortCode(gerarCodigo());
        link.setClicks(0);

        return repository.save(link);
    }

    // buscar
    public Optional<Link> buscar(String code) {
        return repository.findByShortCode(code);
    }

    // listar todos
    public List<Link> listarTodos() {
        return repository.findAll();
    }

    // ranking TOP 5
    public List<Link> rankingTop5() {
        return repository.findAll()
                .stream()
                .sorted((a, b) -> Integer.compare(b.getClicks(), a.getClicks()))
                .limit(5)
                .toList();
    }

    // incrementa clicks no banco
    @Transactional
    public void incrementarClicks(String code) {
        repository.incrementClicks(code);
    }

    private String gerarCodigo() {
        return UUID.randomUUID()
                .toString()
                .substring(0, 6);
    }
}