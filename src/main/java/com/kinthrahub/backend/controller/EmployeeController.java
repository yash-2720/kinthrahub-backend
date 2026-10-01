package com.kinthrahub.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kinthrahub.backend.dto.request.EmployeeRequestDTO;
import com.kinthrahub.backend.dto.request.UpdateEmployeeRequestDTO;
import com.kinthrahub.backend.dto.response.CurrentEmployeeResponseDTO;
import com.kinthrahub.backend.dto.response.EmployeeResponseDTO;
import com.kinthrahub.backend.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addEmployee")
	public EmployeeResponseDTO addEmployee(@Valid @RequestBody EmployeeRequestDTO request) {

		return employeeService.addNewEmployee(request);

	}

	@GetMapping("/getById/{id}")
	public EmployeeResponseDTO getEmployeeById(@PathVariable String id) {
		return employeeService.getEmployeeById(id);
	}
	
	@GetMapping("/getCurrentEmployee")
	public CurrentEmployeeResponseDTO getCurrentEmployee() {
		return employeeService.getCurrentEmployee();
	}

	@GetMapping("/getAllEmployees")
	public Page<EmployeeResponseDTO> getAllEmployees(@RequestParam(defaultValue = "true") boolean isActive,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestParam(defaultValue ="asc") String sortOrder) {
		return employeeService.getAllEmployees(isActive, page, size, sortOrder);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteEmployee/{id}")
	public EmployeeResponseDTO softDeleteEmployee(@PathVariable String id) {
		return employeeService.softDeleteEmployee(id);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateEmployee/{id}")
	public EmployeeResponseDTO updateEmployee(@PathVariable String id,
			@Valid @RequestBody UpdateEmployeeRequestDTO request) {
		return employeeService.updateEmployee(id, request);
	}

	@GetMapping("/search")
	public Page<EmployeeResponseDTO> getEmployees(@RequestParam(required = false) String search,
			@RequestParam(defaultValue = "true") boolean isActive, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue ="asc") String sortOrder) {

		return employeeService.searchEmployee(search, isActive, page, size, sortOrder);
	}

}
