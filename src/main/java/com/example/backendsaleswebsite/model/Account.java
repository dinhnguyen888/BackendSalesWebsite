package com.example.backendsaleswebsite.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String password;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = true)
	private String address;
	
	@Column(nullable = false)
	private Long phoneNumber;
	
	@Column(nullable = false)
	private Boolean role;
	
	@Column(nullable = false)
	private Long voucherID;
}
