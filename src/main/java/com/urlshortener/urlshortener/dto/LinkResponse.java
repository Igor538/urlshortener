package com.urlshortener.urlshortener.dto;

import com.urlshortener.urlshortener.entity.Link;

public class LinkResponse {

    private String shortCode;
    private String originalUrl;
    private int clicks;

    public LinkResponse(Link link) {
        this.shortCode = link.getShortCode();
        this.originalUrl = link.getOriginalUrl();
        this.clicks = link.getClicks();
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public int getClicks() {
        return clicks;
    }
}