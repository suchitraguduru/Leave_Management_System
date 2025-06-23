package com.leavemanagement.LeaveManagement.security;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.leavemanagement.LeaveManagement.controller.ManagerController;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	//Filter to check the users validation
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		try {
			logger.info("Entered into Internal filter");
			//get the token from header
			String token = getToken(request);
			//check the token either valid or not
			if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
				String email = jwtTokenProvider.getEmailFromToken(token);
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
			//load the user and setAuthentication
		}catch(Exception e) {
			throw e;
		}
		
		
	}
	
	//prints token
	private String getToken(HttpServletRequest request) {
		try {
			logger.info("getting token");
			String token = request.getHeader("Authorization");
			logger.info("Got token {}",token);
			if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
				logger.info("valid token");
				return token.substring(7,token.length());
			}
			return null;
			
		}catch(Exception e) {
			throw e;
		}
		
	}
	
}
