package com.example.backendsaleswebsite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@Entity
public class Account{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = true)
	private String address;
	
	@Column(nullable = true)
	private Long phoneNumber;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;
  
	public enum Role {
		User,
		Admin;
	}
}
