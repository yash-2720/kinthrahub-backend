package com.kinthrahub.backend.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kinthrahub.backend.dto.request.EmployeeRequestDTO;
import com.kinthrahub.backend.dto.request.UpdateEmployeeRequestDTO;
import com.kinthrahub.backend.dto.response.CurrentEmployeeResponseDTO;
import com.kinthrahub.backend.dto.response.EmployeeResponseDTO;
import com.kinthrahub.backend.entity.Employee;
import com.kinthrahub.backend.exception.BusinessValidationException;
import com.kinthrahub.backend.exception.ResourceNotFoundException;
import com.kinthrahub.backend.mapper.EmployeeMapper;
import com.kinthrahub.backend.repository.ApplicationUserRepository;
import com.kinthrahub.backend.repository.EmployeeRepository;
import com.kinthrahub.backend.security.LoggedInUserService;
import com.kinthrahub.backend.sequence.SequenceGenerator;
import com.kinthrahub.backend.service.EmployeeService;
import com.kinthrahub.backend.specification.EmployeeSpecification;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;
	private EmployeeMapper employeeMapper;
	private SequenceGenerator sequenceGenerator;
	private ApplicationUserRepository applicationUserRepository;
	private LoggedInUserService loggedInUserService;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper,
			SequenceGenerator sequenceGenerator, ApplicationUserRepository applicationUserRepository, LoggedInUserService loggedInUserService) {
		this.employeeRepository = employeeRepository;
		this.employeeMapper = employeeMapper;
		this.sequenceGenerator = sequenceGenerator;
		this.applicationUserRepository = applicationUserRepository;
		this.loggedInUserService = loggedInUserService;
	}

	@Override
	public EmployeeResponseDTO addNewEmployee(EmployeeRequestDTO request) {

		if (employeeRepository.existsByEmployeeNumber(request.getEmployeeNumber())) {
			throw new BusinessValidationException(
					"Employee already exists with employee number : " + request.getEmployeeNumber());
		}

		if (employeeRepository.existsByEmployeePhoneNumber(request.getEmployeePhoneNumber())) {
			throw new BusinessValidationException(
					"Employee already exists with Phone number : " + request.getEmployeePhoneNumber());
		}
		if (employeeRepository.existsByEmployeeEmail(request.getEmployeeEmail())) {
			throw new BusinessValidationException(
					"Employee already exists with Email Id : " + request.getEmployeeEmail());
		}

		Employee employee = employeeMapper.toEntity(request);
		employee.setEmployeeId(sequenceGenerator.generateId("EMP"));
		employeeRepository.save(employee);
		return employeeMapper.toResponseDTO(employee);
	}

//	@Override
//	public EmployeeResponseDTO getEmployeeById(String id) {
//		Employee employee = employeeRepository.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
//		return employeeMapper.toResponseDTO(employee);
//	}
	@Override
	public EmployeeResponseDTO getEmployeeById(String id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

		EmployeeResponseDTO response = employeeMapper.toResponseDTO(employee);

		response.setApplicationUserCreated(
				applicationUserRepository.existsByEmployeeEmployeeId(employee.getEmployeeId()));

		return response;
	}

	public CurrentEmployeeResponseDTO getCurrentEmployee() {
		
		Employee employee = loggedInUserService.getCurrentEmployee();
		CurrentEmployeeResponseDTO response = employeeMapper.toCurrentEmployeeResponseDTO(employee);
		return response;

	}

	@Override
	public Page<EmployeeResponseDTO> getAllEmployees(boolean isActive, int page, int size, String sortOrder) {
		Specification<Employee> specification = Specification.where(EmployeeSpecification.isActive(isActive));
		Sort sort;
		if ("desc".equalsIgnoreCase(sortOrder)) {
			sort = Sort.by(Sort.Direction.DESC, "employeeName");
		} else {
			sort = Sort.by(Sort.Direction.ASC, "employeeName");
		}

		Page<Employee> employees = employeeRepository.findAll(specification, PageRequest.of(page, size, sort));

		return mapEmployeesWithApplicationUserStatus(employees);

//		    Page<Employee> employees =
//		            employeeRepository.findAll(specification,
//		                    PageRequest.of(page, size, sort));
//
//		    return employees.map(employeeMapper::toResponseDTO);

	}

	@Override
	public EmployeeResponseDTO softDeleteEmployee(String id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
		employee.setActive(false);
		employeeRepository.save(employee);
		return employeeMapper.toResponseDTO(employee);
	}

	@Override
	public EmployeeResponseDTO updateEmployee(String id, UpdateEmployeeRequestDTO request) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
		employeeMapper.updateEntity(employee, request);
		employeeRepository.save(employee);
		return employeeMapper.toResponseDTO(employee);

	}

	@Override
	public Page<EmployeeResponseDTO> searchEmployee(String search, boolean isActive, int page, int size, String sortOrder) {
		Specification<Employee> specification = Specification.where(EmployeeSpecification.search(search))
				.and(EmployeeSpecification.isActive(isActive));
		
		Sort sort;
		if ("desc".equalsIgnoreCase(sortOrder)) {
			sort = Sort.by(Sort.Direction.DESC, "employeeName");
		} else {
			sort = Sort.by(Sort.Direction.ASC, "employeeName");
		}
		
		Page<Employee> employees = employeeRepository.findAll(specification, PageRequest.of(page, size, sort));
		
		
//		return employees.map(employeeMapper::toResponseDTO);
		return mapEmployeesWithApplicationUserStatus(employees);
	}

	private Page<EmployeeResponseDTO> mapEmployeesWithApplicationUserStatus(Page<Employee> employees) {
		if (employees.isEmpty()) {
			return employees.map(employeeMapper::toResponseDTO);
		}
		List<String> employeeIds = employees.getContent().stream().map(Employee::getEmployeeId).toList();

		Set<String> existingEmployeeIds = new HashSet<>(applicationUserRepository.findExistingEmployeeIds(employeeIds));

		return employees.map(employee -> {

			EmployeeResponseDTO response = employeeMapper.toResponseDTO(employee);

			response.setApplicationUserCreated(existingEmployeeIds.contains(employee.getEmployeeId()));

			return response;
		});
	}

}
