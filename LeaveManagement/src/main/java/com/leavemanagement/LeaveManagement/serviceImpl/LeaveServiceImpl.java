package com.leavemanagement.LeaveManagement.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leavemanagement.LeaveManagement.entity.Users;
import com.leavemanagement.LeaveManagement.payload.UserDto;
import com.leavemanagement.LeaveManagement.services.KafkaProducerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.leavemanagement.LeaveManagement.entity.Leave;
import com.leavemanagement.LeaveManagement.exception.APIException;
import com.leavemanagement.LeaveManagement.exception.InvalidLeaveDatesException;
import com.leavemanagement.LeaveManagement.exception.ResourceNotFoundException;
import com.leavemanagement.LeaveManagement.payload.LeaveDto;
import com.leavemanagement.LeaveManagement.payload.StatusDto;
import com.leavemanagement.LeaveManagement.repository.LeaveRepo;
import com.leavemanagement.LeaveManagement.repository.UserRepo;
import com.leavemanagement.LeaveManagement.services.LeaveService;

@Service
public class LeaveServiceImpl implements LeaveService{
	
	private static final Logger logger = LoggerFactory.getLogger(LeaveServiceImpl.class);
	
	@Autowired
	private LeaveRepo leaveRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private KafkaProducerService kafkaProducerService;

	//Service method to apply for leave
	@Override
	@CacheEvict(value = "employeeLeaves", key = "#userId")
	public LeaveDto applyLeave(LeaveDto leaveDto, Integer userId) throws InvalidLeaveDatesException {
		logger.info("Entering into apply leave method");

		// Fetch user from DB
		Users user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "user id", userId));

		// Validate dates
		if (leaveDto.getStartDate().after(leaveDto.getEndDate())) {
			throw new InvalidLeaveDatesException("Start date must be before end date");
		}

		// Set user and status
		leaveDto.setUserId(userId);
		leaveDto.setStatus("Pending");

		// Map and save leave
		Leave leave = this.modelMapper.map(leaveDto, Leave.class);
		Leave savedLeave = this.leaveRepo.save(leave);

		// Prepare email payload
		String emailBody = "Hi " + user.getEmail() + ", your leave has been applied successfully!";
		Map<String, String> emailPayload = Map.of(
				"to", user.getEmail(),
				"subject", "Leave Applied",
				"body", emailBody
		);

		try {
			// Serialize and send via Kafka
			ObjectMapper mapper = new ObjectMapper(); // or @Autowired as a bean
			String jsonPayload = mapper.writeValueAsString(emailPayload);
			kafkaProducerService.sendEmailEvent(jsonPayload);
		} catch (JsonProcessingException e) {
			logger.error("Failed to serialize email payload", e);
		}

		return this.modelMapper.map(savedLeave, LeaveDto.class);
	}

	//Service method to get leaves by employee id
	@Override
	@Cacheable(value = "employeeLeaves", key = "#userId")
	public List<LeaveDto> getLeavesByEmployeeId(Integer userId) throws ResourceNotFoundException{
		logger.info("Entering into  getleavesbyemployeeid method");
		System.out.println(">>> Fetching user leaves from DB...");
		userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","user id",userId));
		List<Leave> leaves = this.leaveRepo.findByUserId(userId);
		List<LeaveDto> leaveDtos = leaves.stream().map((leave)->this.modelMapper.map(leave, LeaveDto.class)).collect(Collectors.toList());
		return leaveDtos;
	}

	//Service method to get the pending leaves
	@Override
	@Cacheable(value = "pendingLeaves")
	public List<LeaveDto> getPendingLeaves() {
		logger.info("Entering into  getPendingleaves method");
		List<Leave> leaves = this.leaveRepo.findByStatus("Pending");
		List<LeaveDto> leaveDtos = leaves.stream().map((leave)->this.modelMapper.map(leave, LeaveDto.class)).collect(Collectors.toList());
		return leaveDtos;
	}

	//Service method to approve the leave
	@Override
	@CacheEvict(value = {"pendingLeaves", "allLeaves"}, allEntries = true)
	public LeaveDto setStatus(StatusDto statusDto, Integer id) {
		logger.info("Entering into approve leave method");
		Leave leave  = this.leaveRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("leave","leave id",id));
		if(leave.getStatus().equals("Pending")) {
			leave.setStatus(statusDto.getStatus());
			leave.setComment(statusDto.getComment());
		}
		Leave savedLeave = this.leaveRepo.save(leave);
		return this.modelMapper.map(savedLeave,LeaveDto.class);
	}
	
	
	//Service method to get all leaves
	@Override
	@Cacheable(value = "allLeaves")
	public List<LeaveDto> getAllLeaves() {
		logger.info("Entering into  getAllLeaves method");
//		System.out.println(">>> Fetching all leaves from DB...");
		List<Leave> leaves = this.leaveRepo.findAll();
		List<LeaveDto> leaveDtos = leaves.stream().map((leave)->this.modelMapper.map(leave, LeaveDto.class)).collect(Collectors.toList());
		return leaveDtos;
	}
	
}
