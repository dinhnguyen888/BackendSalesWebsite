package com.example.backendsaleswebsite.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PUBLIC)
public class AuthenticationResponse {
	String token;
    boolean authenticated;
}