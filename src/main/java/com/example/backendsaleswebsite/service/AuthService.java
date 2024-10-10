package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.AuthenticationRequest;
import com.example.backendsaleswebsite.dto.AuthenticationResponse;
import com.example.backendsaleswebsite.dto.IntrospectRequest;
import com.example.backendsaleswebsite.dto.IntrospectResponse;
import com.example.backendsaleswebsite.dto.LoginRequest;
import com.example.backendsaleswebsite.exception.AppException;
import com.example.backendsaleswebsite.exception.ErrorCode;
import com.example.backendsaleswebsite.jwt.JwtUtil;
import com.example.backendsaleswebsite.dto.RegisterRequest; // Nhập RegisterRequest
import com.example.backendsaleswebsite.model.Account;
import com.example.backendsaleswebsite.repository.AccountRepository;

import java.util.Optional;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public class AuthService {
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private String SIGNER_KEY = "GaHQG6SjkCOQBF5yspA4Bd+t1EGA1gP+UP++0odDou9MUNdArwKwCX1kmqtSlEhQ";

	public AuthenticationResponse login(AuthenticationRequest request) {
        var user = accountRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
	}
	
	public String generateToken(Account account) {
		
		Logger log = LoggerFactory.getLogger(JwtUtil.class);
		
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
		
		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
				.subject(account.getEmail())
				.issuer("4TNGears.com")
				.issueTime(new Date())
				.expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
				.build();
		
		Payload payload = new Payload(jwtClaimsSet.toJSONObject());
		
		JWSObject jwsObject = new JWSObject(header, payload);
		
		try {
			jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
			return jwsObject.serialize();
		} catch (JOSEException e) {
			log.error("Cannot create token", e);
			throw new RuntimeException(e);
		}
	}
	
	public IntrospectResponse introspect(IntrospectRequest request) 
			throws JOSEException, ParseException{
		
		var token = request.getToken();
		
		JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
		
		SignedJWT signedJWT = SignedJWT.parse(token);
		
		Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
		
		var verified = signedJWT.verify(verifier);
		
		return IntrospectResponse.builder().valid(verified && expiryTime.after(new Date())).build();
	}

	// ham register
	public boolean register(RegisterRequest registerRequest) {
		Optional<Account> existingAccount = accountRepository.findByEmail(registerRequest.email());


	    if (existingAccount.isPresent()) {
	        return false; // Tài khoản đã tồn tại
	    }

	    // Tạo tài khoản mới
	  
	    Account newAccount = new Account();
	    newAccount.setEmail(registerRequest.email());
	    newAccount.setUserName(registerRequest.userName());
	    newAccount.setPassword(passwordEncoder.encode(registerRequest.password()));
	    newAccount.setRole(Account.Role.User);
	    accountRepository.save(newAccount); 
	    return true; 
	}

}
