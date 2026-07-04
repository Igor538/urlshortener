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

    public Link encurtar(String originalUrl, String userId) {

        Link link = new Link();

        link.setOriginalUrl(originalUrl);
        link.setShortCode(gerarCodigo());
        link.setClicks(0);
        link.setUserId(userId);

        return repository.save(link);
    }

    public Optional<Link> buscar(String code) {
        return repository.findByShortCode(code);
    }

    public List<Link> listarPorUsuario(String userId) {
        return repository.findByUserId(userId);
    }

    public List<Link> rankingTop5(String userId) {
        return repository.findTop5ByUserIdOrderByClicksDesc(userId);
    }

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