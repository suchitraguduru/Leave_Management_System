package com.leavemanagement.LeaveManagement.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leavemanagement.LeaveManagement.exception.InvalidLeaveDatesException;
import com.leavemanagement.LeaveManagement.payload.BackendResponse;
import com.leavemanagement.LeaveManagement.payload.LeaveDto;
import com.leavemanagement.LeaveManagement.services.LeaveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee/leave")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private LeaveService leaveService;
	
	//post method to create and applying for leave
	@PostMapping("/{userId}")
	public ResponseEntity<BackendResponse> createLeave(@RequestBody LeaveDto leaveDto,@PathVariable Integer userId) throws InvalidLeaveDatesException{
		logger.info("Received request to /employee/leave/userid");
		BackendResponse backendResponse = new BackendResponse();
		try {
			StringBuilder error = new StringBuilder("Field ");
			
			if(leaveDto.getLeaveType().isBlank()) {
				error.append("LeaveType is not mentioned");
			}
			if(leaveDto.getStartDate().toString().isEmpty()) {
				error.append("Start Date should not be empty");
			}
			if(leaveDto.getEndDate().toString().isEmpty()) {
				error.append("End Date should not be empty");
			}
			if(leaveDto.getReason().isEmpty()) {
				error.append("Reason is not mentioned");
			}
			if(!error.toString().equals("Field ")) {
				backendResponse.setMessage(error.toString());
				backendResponse.setStatus("fail");
				backendResponse.setData("false");
				return new ResponseEntity<>(backendResponse,HttpStatus.BAD_REQUEST);
			}
			LeaveDto savedDto = this.leaveService.applyLeave(leaveDto, userId);
			backendResponse.setMessage("Leave created Succesfully");
			backendResponse.setStatus("Pass");
			backendResponse.setData("Saved");
			return new ResponseEntity<>(backendResponse,HttpStatus.CREATED);
		}catch(Exception e) {
			backendResponse.setMessage("Apply leave Unsuccesssful");
			backendResponse.setStatus("fail");
			backendResponse.setData(e);
			return new ResponseEntity<>(backendResponse,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	//get method to get all the leaves by userid
	@GetMapping("/users/{userId}")
	public ResponseEntity<BackendResponse> getLeaveByUserId(@PathVariable Integer userId){
		logger.info("Received request to /employee/users/userid");
		BackendResponse backendResponse = new BackendResponse();
		try {
			List<LeaveDto> leaveDto = this.leaveService.getLeavesByEmployeeId(userId);
			backendResponse.setMessage("Getting leaves of user");
			backendResponse.setStatus("pass");
			backendResponse.setData(leaveDto);
			return new ResponseEntity<>(backendResponse,HttpStatus.OK);
		}catch(Exception e) {
			backendResponse.setMessage("Error occured in retrieving leaves");
			backendResponse.setStatus("fail");
			backendResponse.setData(e);
			return new ResponseEntity<>(backendResponse,HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	
}

