package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.LoginRequest;
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
	
	public boolean login(LoginRequest loginRequest) {
		Optional<Account> accountOptional = accountRepository.findByEmail(loginRequest.getEmail());
		
		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();
			return passwordEncoder.matches(loginRequest.getPassword(), account.getPassword());
		}
		
		return false;
	}
}
