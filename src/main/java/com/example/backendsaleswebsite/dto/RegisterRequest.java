package com.example.backendsaleswebsite.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public record RegisterRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId,

    @Column(unique = true, nullable = false)
	String email,
    
    @Column(nullable = false)
    String userName,

    @Column(nullable = false)
    String password,

    @Column(nullable = false)
    Boolean role
) {}
