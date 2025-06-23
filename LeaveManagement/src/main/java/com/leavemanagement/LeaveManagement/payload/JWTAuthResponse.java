package com.leavemanagement.LeaveManagement.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {
	private String token;
	private String tokenType="Bearer";
	public JWTAuthResponse(String token) {
		this.token = token;
	}
}
