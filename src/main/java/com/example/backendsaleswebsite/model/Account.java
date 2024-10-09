package com.example.backendsaleswebsite.model;

import jakarta.persistence.*;

@Entity
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
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	//Getters and Setters
	public Long getUserId() {
		return userId;
	}
		
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	
	public enum Role{
		 User, Admin;
	}
}
