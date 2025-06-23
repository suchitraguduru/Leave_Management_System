package com.leavemanagement.LeaveManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leavemanagement.LeaveManagement.entity.Leave;

public interface LeaveRepo extends JpaRepository<Leave,Integer>{
	List<Leave> findByUserId(Integer userId);
	
    List<Leave> findByStatus(String status);
}
