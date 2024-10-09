package com.example.backendsaleswebsite.dto;

import com.example.backendsaleswebsite.model.Account.Role; // Import Role từ Account

public record RegisterRequest(
    String email,
    String userName,
    String password,
    Role role // Sử dụng enum Role từ Account
) {}
