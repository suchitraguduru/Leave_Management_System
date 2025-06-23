package com.leavemanagement.LeaveManagement.entity;


public enum Role {
	MANAGER,
    EMPLOYEE;

	public String getRoleName() {
		return "ROLE_"+this.name();
	}
    
}
