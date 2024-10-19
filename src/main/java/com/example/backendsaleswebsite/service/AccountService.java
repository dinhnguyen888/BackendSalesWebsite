package com.example.backendsaleswebsite.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backendsaleswebsite.dto.AccountResponse;
import com.example.backendsaleswebsite.exception.AppException;
import com.example.backendsaleswebsite.exception.ErrorCode;
import com.example.backendsaleswebsite.model.Account;
import com.example.backendsaleswebsite.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
    private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    public Account createAccount(Account account) {
    	account.setPassword(passwordEncoder.encode(account.getPassword()));
    	return accountRepository.save(account);
    }

    @PreAuthorize("hasRole('Admin')")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    
    @PostAuthorize("returnObject.isPresent() && returnObject.get().email == authentication.name")
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }
    
    public AccountResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Account user = accountRepository.findByEmail(name)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        AccountResponse userResponse = new AccountResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setUserName(user.getUserName());
        userResponse.setEmail(user.getEmail());
        userResponse.setAddress(user.getAddress());
        userResponse.setPhoneNumber(user.getPhoneNumber());

        return userResponse;
    }

    public Account updateAccount(Long id, Account accountDetails) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account không tồn tại"));

        account.setUserName(accountDetails.getUserName());
        account.setEmail(accountDetails.getEmail());
        
        if (!account.getPassword().equals(accountDetails.getPassword())) {
            account.setPassword(passwordEncoder.encode(accountDetails.getPassword()));
        }
        
        account.setAddress(accountDetails.getAddress());
        account.setPhoneNumber(accountDetails.getPhoneNumber());
        account.setRole(accountDetails.getRole());

        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account không tồn tại"));
        accountRepository.delete(account);
    }
}
