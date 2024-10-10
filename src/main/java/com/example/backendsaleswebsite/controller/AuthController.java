package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.dto.ApiResponse;
import com.example.backendsaleswebsite.dto.AuthenticationRequest;
import com.example.backendsaleswebsite.dto.AuthenticationResponse;
import com.example.backendsaleswebsite.dto.IntrospectRequest;
import com.example.backendsaleswebsite.dto.IntrospectResponse;
import com.example.backendsaleswebsite.dto.LoginRequest;
import com.example.backendsaleswebsite.dto.RegisterRequest; // Đừng quên import RegisterRequest
import com.example.backendsaleswebsite.service.AuthService;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authService.login(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }
	
	@PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) 
    		throws JOSEException, ParseException {
        var result = authService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
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
