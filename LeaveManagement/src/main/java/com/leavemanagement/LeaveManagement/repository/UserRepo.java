package com.leavemanagement.LeaveManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leavemanagement.LeaveManagement.entity.Users;

public interface UserRepo extends JpaRepository<Users,Integer>{

	Optional<Users> findByEmail(String email);
//	<Optional> Users findByEmail(String email);
}
