package com.example.backendsaleswebsite.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendsaleswebsite.model.Account;
import com.example.backendsaleswebsite.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Account updateAccount(Long id, Account accountDetails) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account không tồn tại"));

        account.setUserName(accountDetails.getUserName());
        account.setEmail(accountDetails.getEmail());
        account.setPassword(accountDetails.getPassword());
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
