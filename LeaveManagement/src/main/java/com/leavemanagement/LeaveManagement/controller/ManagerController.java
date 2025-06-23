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

import com.leavemanagement.LeaveManagement.payload.BackendResponse;
import com.leavemanagement.LeaveManagement.payload.LeaveDto;
import com.leavemanagement.LeaveManagement.payload.StatusDto;
import com.leavemanagement.LeaveManagement.services.LeaveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/manager/leave")
@PreAuthorize("hasRole('MANAGER')")
public class ManagerController {
	
	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	
	@Autowired
	private LeaveService leaveService;

	//Get method to retrieve all the pending methods
	@GetMapping("/pending")
	public ResponseEntity<BackendResponse> getPendingLeaves(){
		logger.info("Received request to /manager/leave/pending");
		BackendResponse backendResponse = new BackendResponse();
		try {
			List<LeaveDto> leaveDto = this.leaveService.getPendingLeaves();
			backendResponse.setMessage("Getting leaves of user");
			backendResponse.setStatus("pass");
			backendResponse.setData(leaveDto);
			return new ResponseEntity<>(backendResponse,HttpStatus.OK);
		}catch(Exception e) {
			backendResponse.setMessage("Error occured in retrieving pending leaves");
			backendResponse.setStatus("fail");
			backendResponse.setData(e);
			return new ResponseEntity<>(backendResponse,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	//Get method to retreive all users leaves
	@GetMapping("/leaves")
	public ResponseEntity<BackendResponse> getAllLeaves(){
		logger.info("Received request to /manager/leave/leaves");
		BackendResponse backendResponse = new BackendResponse();
		try {
			List<LeaveDto> leaveDto = this.leaveService.getAllLeaves();
			backendResponse.setMessage("Getting leaves of users");
			backendResponse.setStatus("pass");
			backendResponse.setData(leaveDto);
			return new ResponseEntity<>(backendResponse,HttpStatus.OK);
		}catch(Exception e) {
			backendResponse.setMessage("Error occured in retrieving all leaves");
			backendResponse.setStatus("fail");
			backendResponse.setData(e);
			return new ResponseEntity<>(backendResponse,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	//put method to approve or reject leave
	@PutMapping("/setstatus/{id}")
	public ResponseEntity<BackendResponse> approveLeave(@Valid @RequestBody StatusDto statusDto,@PathVariable Integer id){
		logger.info("Received request to /manager/leave/setstatus");
		BackendResponse backendResponse = new BackendResponse();
		try {
			StringBuilder error = new StringBuilder("Field ");
			logger.info("=============="+error);
			if(statusDto.getStatus()==null) {
				error.append("Status is not mentioned");
			}
			if(statusDto.getStatus().isEmpty()) {
				error.append("Status should not be empty");
			}
			if(statusDto.getComment()==null) {
				error.append("Comment is not mentioned");
			}
			if(statusDto.getComment().isEmpty()) {
				error.append("Comment should not be empty");
			}
			
			if(!error.toString().equals("Field ")) {
				backendResponse.setMessage(error.toString());
				backendResponse.setStatus("fail");
				backendResponse.setData("false");
				return new ResponseEntity<>(backendResponse,HttpStatus.BAD_REQUEST);
			}
			LeaveDto leaveDto = this.leaveService.setStatus(statusDto,id);
			backendResponse.setMessage("Getting leaves of user");
			backendResponse.setStatus("pass");
			backendResponse.setData(leaveDto);
			return new ResponseEntity<>(backendResponse,HttpStatus.OK);
		}catch(Exception e) {
			backendResponse.setMessage("Error in Status setting");
			backendResponse.setStatus("fail");
			backendResponse.setData(e);
			return new ResponseEntity<>(backendResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	
}

