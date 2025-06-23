package com.leavemanagement.LeaveManagement.security;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.leavemanagement.LeaveManagement.Config.AppConstants;
import com.leavemanagement.LeaveManagement.exception.APIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
		private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
		
		//generates user token
		public String generateToken(Authentication authentication) {
			try {
				logger.info("generating token");
				String email = authentication.getName();
				Date currentDate = new Date();
				Date expireDate = new Date(currentDate.getTime()+360000000); //milliseconds 60 minutes expiry time
				String token = Jwts.builder()
						.setSubject(email)
						.setIssuedAt(currentDate)
						.setExpiration(expireDate)
						.signWith(SignatureAlgorithm.HS512, AppConstants.JWT_KEY)
						.compact();
				logger.info("token generated successfully");
				return token;
				
			}catch(Exception e) {
				throw e;
			}
			
		}
		
		//retrieve email from token
		public String getEmailFromToken(String token) {
			try {
				logger.info("retrieving email from token");
				Claims claims = Jwts.parser().setSigningKey(AppConstants.JWT_KEY)
						.parseClaimsJws(token).getBody();
						return claims.getSubject();
			}catch(Exception e) {
				throw e;
			}
			
		}
		
		public boolean validateToken(String token) {
			try {
				logger.info("validating token");
				Jwts.parser().setSigningKey(AppConstants.JWT_KEY)
				.parseClaimsJws(token);
				logger.info("token validated successfully");
				return true;
			}catch(Exception e) {
				throw new APIException("token issue :"+ e.getMessage());
			}
		}
}
