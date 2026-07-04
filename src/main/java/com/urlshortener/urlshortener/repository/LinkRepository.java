package com.urlshortener.urlshortener.repository;

import com.urlshortener.urlshortener.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByShortCode(String shortCode);

    @Modifying
    @Query("UPDATE Link l SET l.clicks = l.clicks + 1 WHERE l.shortCode = :code")
    void incrementClicks(@Param("code") String code);

    // 🔥 TOP 5 ranking direto no banco
    List<Link> findTop5ByOrderByClicksDesc();
}