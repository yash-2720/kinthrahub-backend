package com.kinthrahub.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kinthrahub.backend.dto.response.EmployeeResponseDTO;
import com.kinthrahub.backend.entity.Employee;
import com.kinthrahub.backend.mapper.EmployeeMapper;
import com.kinthrahub.backend.repository.ApplicationUserRepository;
import com.kinthrahub.backend.repository.EmployeeRepository;
import com.kinthrahub.backend.security.LoggedInUserService;
import com.kinthrahub.backend.sequence.SequenceGenerator;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private EmployeeMapper employeeMapper;

	@Mock
	private SequenceGenerator sequenceGenerator;

	@Mock
	private ApplicationUserRepository applicationUserRepository;

	@Mock
	private LoggedInUserService loggedInUserService;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Test
	void getEmployeeById_getExistingEmployee_returnsEmployee() {

		String employeeId = "EMP00000001";

		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);

		when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

		EmployeeResponseDTO response = new EmployeeResponseDTO();
		response.setEmployeeId(employeeId);

		when(employeeMapper.toResponseDTO(employee)).thenReturn(response);
		
		when(applicationUserRepository.existsByEmployeeEmployeeId(employeeId)).thenReturn(true);
		
	    // Act
	    EmployeeResponseDTO result =
	            employeeService.getEmployeeById(employeeId);

	    // Assert
	    assertEquals(employeeId, result.getEmployeeId());
	    assertEquals(true, result.isApplicationUserCreated());
	}
}
