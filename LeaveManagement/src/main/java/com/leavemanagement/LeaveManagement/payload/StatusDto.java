package com.leavemanagement.LeaveManagement.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class StatusDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status;
	private String comment;
}
