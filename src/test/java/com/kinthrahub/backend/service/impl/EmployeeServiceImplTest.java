package com.kinthrahub.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kinthrahub.backend.dto.request.EmployeeRequestDTO;
import com.kinthrahub.backend.dto.response.EmployeeResponseDTO;
import com.kinthrahub.backend.entity.Employee;
import com.kinthrahub.backend.exception.BusinessValidationException;
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

	private EmployeeRequestDTO requestDTO;
	private Employee employee;
	private EmployeeResponseDTO responseDTO;

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
		EmployeeResponseDTO result = employeeService.getEmployeeById(employeeId);

		// Assert
		assertEquals(employeeId, result.getEmployeeId());
		assertEquals(true, result.isApplicationUserCreated());
	}

	@BeforeEach
	void setUp() {
		requestDTO = new EmployeeRequestDTO();
		requestDTO.setEmployeeNumber("EMP123");
		requestDTO.setEmployeePhoneNumber("1234567890");
		requestDTO.setEmployeeEmail("test@example.com");

		employee = new Employee();
		employee.setEmployeeNumber("EMP123");
		employee.setEmployeePhoneNumber("1234567890");
		employee.setEmployeeEmail("test@example.com");

		responseDTO = new EmployeeResponseDTO();
		responseDTO.setEmployeeNumber("EMP123");
	}

	@Test
	void addNewEmployee_Success_ReturnsResponseDTO() {
		// Arrange
		when(employeeRepository.existsByEmployeeNumber(requestDTO.getEmployeeNumber())).thenReturn(false);
		when(employeeRepository.existsByEmployeePhoneNumber(requestDTO.getEmployeePhoneNumber())).thenReturn(false);
		when(employeeRepository.existsByEmployeeEmail(requestDTO.getEmployeeEmail())).thenReturn(false);
		when(employeeMapper.toEntity(requestDTO)).thenReturn(employee);
		when(sequenceGenerator.generateId("EMP")).thenReturn("SEQ_001");
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
		when(employeeMapper.toResponseDTO(employee)).thenReturn(responseDTO);

		// Act
		EmployeeResponseDTO result = employeeService.addNewEmployee(requestDTO);

		// Assert
		assertNotNull(result);
		assertEquals(responseDTO.getEmployeeNumber(), result.getEmployeeNumber());

		verify(employeeRepository).existsByEmployeeNumber(requestDTO.getEmployeeNumber());
		verify(employeeRepository).existsByEmployeePhoneNumber(requestDTO.getEmployeePhoneNumber());
		verify(employeeRepository).existsByEmployeeEmail(requestDTO.getEmployeeEmail());
		verify(employeeMapper).toEntity(requestDTO);
		verify(sequenceGenerator).generateId("EMP");
		verify(employeeRepository).save(employee);
		verify(employeeMapper).toResponseDTO(employee);
		assertEquals("SEQ_001", employee.getEmployeeId());
	}

	@Test
	void addNewEmployee_DuplicateEmployeeNumber_ThrowsBusinessValidationException() {
		// Arrange
		when(employeeRepository.existsByEmployeeNumber(requestDTO.getEmployeeNumber())).thenReturn(true);

		// Act & Assert
		BusinessValidationException exception = assertThrows(BusinessValidationException.class, () -> {
			employeeService.addNewEmployee(requestDTO);
		});

		assertEquals("Employee already exists with employee number : EMP123", exception.getMessage());

		// Verify remaining validations and operations are skipped
		verify(employeeRepository, never()).existsByEmployeePhoneNumber(anyString());
		verify(employeeRepository, never()).existsByEmployeeEmail(anyString());
		verify(employeeRepository, never()).save(any(Employee.class));
	}

	@Test
	void addNewEmployee_DuplicatePhoneNumber_ThrowsBusinessValidationException() {
		// Arrange
		when(employeeRepository.existsByEmployeeNumber(requestDTO.getEmployeeNumber())).thenReturn(false);
		when(employeeRepository.existsByEmployeePhoneNumber(requestDTO.getEmployeePhoneNumber())).thenReturn(true);

		// Act & Assert
		BusinessValidationException exception = assertThrows(BusinessValidationException.class, () -> {
			employeeService.addNewEmployee(requestDTO);
		});

		assertEquals("Employee already exists with Phone number : 1234567890", exception.getMessage());

		// Verify email validation and saving are skipped
		verify(employeeRepository, never()).existsByEmployeeEmail(anyString());
		verify(employeeRepository, never()).save(any(Employee.class));
	}

	@Test
	void addNewEmployee_DuplicateEmail_ThrowsBusinessValidationException() {
		// Arrange
		when(employeeRepository.existsByEmployeeNumber(requestDTO.getEmployeeNumber())).thenReturn(false);
		when(employeeRepository.existsByEmployeePhoneNumber(requestDTO.getEmployeePhoneNumber())).thenReturn(false);
		when(employeeRepository.existsByEmployeeEmail(requestDTO.getEmployeeEmail())).thenReturn(true);

		// Act & Assert
		BusinessValidationException exception = assertThrows(BusinessValidationException.class, () -> {
			employeeService.addNewEmployee(requestDTO);
		});

		assertEquals("Employee already exists with Email Id : test@example.com", exception.getMessage());

		// Verify saving execution is skipped
		verify(employeeRepository, never()).save(any(Employee.class));
	}

}
