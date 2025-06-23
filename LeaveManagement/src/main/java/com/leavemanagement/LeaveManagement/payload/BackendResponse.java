package com.leavemanagement.LeaveManagement.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackendResponse {
	private String status;
	private String message;
	private Object data;
}
