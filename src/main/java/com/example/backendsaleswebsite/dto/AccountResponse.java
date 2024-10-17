package com.example.backendsaleswebsite.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PUBLIC)
public class AccountResponse {
	private Long userId;
    private String userName;
    private String email;
    private String address;
    private Long phoneNumber;
}
