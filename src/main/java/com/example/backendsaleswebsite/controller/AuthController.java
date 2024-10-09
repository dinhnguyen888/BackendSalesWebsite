package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.dto.ApiResponse;
import com.example.backendsaleswebsite.dto.AuthenticationRequest;
import com.example.backendsaleswebsite.dto.AuthenticationResponse;
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
	
	@PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authService.login(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
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
