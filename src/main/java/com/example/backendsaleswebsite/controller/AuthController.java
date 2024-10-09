package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.dto.LoginRequest;
import com.example.backendsaleswebsite.dto.RegisterRequest; // Đừng quên import RegisterRequest
import com.example.backendsaleswebsite.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = authService.login(loginRequest);

        if (isAuthenticated) {
            return ResponseEntity.ok("Đăng nhập thành công!");
        } else {
            return ResponseEntity.status(401).body("Thông tin xác thực không hợp lệ!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        boolean isRegistered = authService.register(registerRequest);

        if (isRegistered) {
            return ResponseEntity.ok("Đăng ký thành công!");
        } else {
            return ResponseEntity.status(400).body("Đăng ký không thành công! Vui lòng thử lại.");
        }
    }
}
