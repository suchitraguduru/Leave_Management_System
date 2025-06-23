package com.leavemanagement.LeaveManagement.services;

import java.util.List;

import com.leavemanagement.LeaveManagement.entity.Leave;
import com.leavemanagement.LeaveManagement.exception.InvalidLeaveDatesException;
import com.leavemanagement.LeaveManagement.exception.ResourceNotFoundException;
import com.leavemanagement.LeaveManagement.payload.LeaveDto;
import com.leavemanagement.LeaveManagement.payload.StatusDto;

public interface LeaveService {
	LeaveDto applyLeave(LeaveDto leaveDto,Integer userId) throws InvalidLeaveDatesException;

    List<LeaveDto> getLeavesByEmployeeId(Integer userId) throws ResourceNotFoundException;
    List<LeaveDto> getAllLeaves();
    List<LeaveDto> getPendingLeaves();

    LeaveDto setStatus(StatusDto statusDto,Integer id);
}
