package com.example.backendsaleswebsite.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtUtil {
	
	public String generateToken(String email) {
		
		Logger log = LoggerFactory.getLogger(JwtUtil.class);
		String SIGNER_KEY = "GaHQG6SjkCOQBF5yspA4Bd+t1EGA1gP+UP++0odDou9MUNdArwKwCX1kmqtSlEhQ";
		
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
		
		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
				.subject(email)
				.issuer("devteria.com")
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
}
