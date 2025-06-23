package com.leavemanagement.LeaveManagement.payload;

import com.leavemanagement.LeaveManagement.entity.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
}
