package com.kinthrahub.backend.service;

import org.springframework.data.domain.Page;

import com.kinthrahub.backend.dto.request.EmployeeRequestDTO;
import com.kinthrahub.backend.dto.request.UpdateEmployeeRequestDTO;
import com.kinthrahub.backend.dto.response.CurrentEmployeeResponseDTO;
import com.kinthrahub.backend.dto.response.EmployeeResponseDTO;


public interface EmployeeService {
	
	public EmployeeResponseDTO addNewEmployee(EmployeeRequestDTO request) ;
	
	public EmployeeResponseDTO getEmployeeById(String id);
	
	public Page<EmployeeResponseDTO> getAllEmployees(boolean isActive, int page, int size, String sortOrder);
	
	public EmployeeResponseDTO softDeleteEmployee(String id);
	
	public EmployeeResponseDTO updateEmployee(String id, UpdateEmployeeRequestDTO request);
	
	public Page<EmployeeResponseDTO> searchEmployee(String search, boolean isActive, int page, int size, String sortOrder);
	
	public CurrentEmployeeResponseDTO getCurrentEmployee();
}
