package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.LoginRequest;
import com.example.backendsaleswebsite.dto.RegisterRequest; // Nhập RegisterRequest
import com.example.backendsaleswebsite.model.Account;
import com.example.backendsaleswebsite.repository.AccountRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Hàm login
	public boolean login(LoginRequest loginRequest) {
		Optional<Account> accountOptional = accountRepository.findByEmail(loginRequest.getEmail());
		
		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();
			return passwordEncoder.matches(loginRequest.getPassword(), account.getPassword());
		}
		
		return false;
	}

	// ham register
	public boolean register(RegisterRequest registerRequest) {
	    // Kiểm tra xem tài khoản đã tồn tại hay chưa
	    Optional<Account> existingAccount = accountRepository.findByEmail(registerRequest.userName()); // Nếu userName là email

	    if (existingAccount.isPresent()) {
	        return false; // Tài khoản đã tồn tại
	    }

	    // Tạo tài khoản mới
	    Account newAccount = new Account();
	    newAccount.setEmail(registerRequest.email()); // Giả sử đây là email
	    newAccount.setUserName(registerRequest.userName());
	    newAccount.setPassword(passwordEncoder.encode(registerRequest.password())); // Mã hóa mật khẩu
	    newAccount.setRole(registerRequest.role()); // Thiết lập vai trò người dùng

	    accountRepository.save(newAccount); // Lưu tài khoản vào cơ sở dữ liệu
	    return true; // Đăng ký thành công
	}

}
