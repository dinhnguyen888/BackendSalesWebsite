package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.dto.LoginRequest;
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
}
