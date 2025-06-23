package com.leavemanagement.LeaveManagement.payload;

import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LeaveDto implements Serializable {
    private static final long serialVersionUID = 1L;
	private int id;
    private int userId;
    
    private String leaveType;
    
    private Date startDate;
    
    private Date endDate;
    
    private String reason;
    private String status;
    private String comment;
}
