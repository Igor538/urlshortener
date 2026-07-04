package com.urlshortener.urlshortener.dto;

public class LinkStatsResponse {

    private String originalUrl;
    private String shortCode;
    private int clicks;

    public LinkStatsResponse(String originalUrl, String shortCode, int clicks) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.clicks = clicks;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public int getClicks() {
        return clicks;
    }
}