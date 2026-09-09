package com.kinthrahub.backend.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.kinthrahub.backend.dto.request.DonationRequestDTO;
import com.kinthrahub.backend.dto.response.DonationRequestResponseDTO;
import com.kinthrahub.backend.dto.response.DonationSummaryResponseDTO;
import com.kinthrahub.backend.entity.ApplicationUser;
import com.kinthrahub.backend.entity.DonationPlan;
import com.kinthrahub.backend.entity.DonationRequest;
import com.kinthrahub.backend.entity.Employee;
import com.kinthrahub.backend.enums.DonationStatus;
import com.kinthrahub.backend.enums.DonationType;
import com.kinthrahub.backend.exception.BusinessValidationException;
import com.kinthrahub.backend.exception.ResourceNotFoundException;
import com.kinthrahub.backend.mapper.DonationRequestMapper;
import com.kinthrahub.backend.repository.DonationPlanRepository;
import com.kinthrahub.backend.repository.DonationRequestRepository;
import com.kinthrahub.backend.repository.EmployeeRepository;
import com.kinthrahub.backend.security.LoggedInUserService;
import com.kinthrahub.backend.sequence.SequenceGenerator;
import com.kinthrahub.backend.service.DonationRequestService;
import com.kinthrahub.backend.specification.DonationRequestSpecification;

@Service
public class DonationRequestServiceImpl implements DonationRequestService {

	private DonationRequestRepository donationRequestRepository;
	private SequenceGenerator sequenceGenerator;
	private EmployeeRepository employeeRepository;
	private DonationPlanRepository donationPlanRepository;
	private DonationRequestMapper donationRequestMapper;
	private LoggedInUserService loggedInUserService;

	public DonationRequestServiceImpl(DonationRequestRepository donationRequestRepository,
			SequenceGenerator sequenceGenerator, EmployeeRepository employeeRepository,
			DonationPlanRepository donationPlanRepository, DonationRequestMapper donationRequestMapper,
			LoggedInUserService loggedInUserService) {

		this.donationRequestRepository = donationRequestRepository;
		this.sequenceGenerator = sequenceGenerator;
		this.employeeRepository = employeeRepository;
		this.donationPlanRepository = donationPlanRepository;
		this.donationRequestMapper = donationRequestMapper;
		this.loggedInUserService = loggedInUserService;
	}

	@Override
	public DonationRequestResponseDTO addDonationRequest(DonationRequestDTO request) {

		DonationRequest donationRequest = donationRequestMapper.toEntity(request);

		Employee employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow(
				() -> new ResourceNotFoundException("Employee not found for Id : " + request.getEmployeeId()));
		DonationPlan donationPlan = donationPlanRepository.findById(request.getDonationPlanId()).orElseThrow(
				() -> new ResourceNotFoundException("Donation Plan not found for Id : " + request.getDonationPlanId()));

		donationRequest.setEmployee(employee);
		donationRequest.setDonationPlan(donationPlan);

		donationRequest.setDonationRequestId(sequenceGenerator.generateId("DOR"));

		BigDecimal salary = employee.getBasicSalary();
		BigDecimal donationAmount = donationRequest.getDonationAmount();
		BigDecimal updatedAmount = salary.subtract(donationAmount);
		if (donationAmount.compareTo(BigDecimal.valueOf(500)) < 0) {
			throw new BusinessValidationException("The minimum donation amount allowed is 500");
		}

		if (updatedAmount.compareTo(BigDecimal.valueOf(5000)) < 0) {
			throw new BusinessValidationException(
					"Insufficient remaining salary. Your account must retain at least 5,000 after donating.");

		}
		donationRequest.setDonationStatus(DonationStatus.INITIALIZED);
		donationRequestRepository.save(donationRequest);
		return donationRequestMapper.toResponseDTO(donationRequest);
	}

	@Override
	public DonationRequestResponseDTO getDonationRequestById(String donationRequestId) {

		DonationRequest donationRequest;

		if (loggedInUserService.isAdmin()) {

			donationRequest = donationRequestRepository.findById(donationRequestId).orElseThrow(
					() -> new ResourceNotFoundException("Donation Request Not found for Id : " + donationRequestId));

		} else {
			donationRequest = donationRequestRepository
					.findByDonationRequestIdAndEmployeeEmployeeId(donationRequestId,
							loggedInUserService.getCurrentEmployee().getEmployeeId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Donation Request Not found for Id : " + donationRequestId));

		}

		return donationRequestMapper.toResponseDTO(donationRequest);
	}

	@Override
	public Page<DonationRequestResponseDTO> getAllDonationRequests(boolean isActive, int page, int size) {
		Specification<DonationRequest> specification = Specification
				.where(DonationRequestSpecification.isActive(isActive));

		if (!loggedInUserService.isAdmin()) {

			specification = specification
					.and(DonationRequestSpecification.employee(loggedInUserService.getCurrentEmployee()));
		}

		Page<DonationRequest> donationRequests = donationRequestRepository.findAll(specification,
				PageRequest.of(page, size));

		return donationRequests.map(donationRequestMapper::toResponseDTO);
	}

	@Override
	public Page<DonationRequestResponseDTO> searchDonationRequests(String search, boolean isActive, int page,
			int size) {

		Specification<DonationRequest> specification = Specification.where(DonationRequestSpecification.search(search))
				.and(DonationRequestSpecification.isActive(isActive));

		if (!loggedInUserService.isAdmin()) {

			specification = specification
					.and(DonationRequestSpecification.employee(loggedInUserService.getCurrentEmployee()));
		}

		Page<DonationRequest> donationRequests = donationRequestRepository.findAll(specification,
				PageRequest.of(page, size));
		return donationRequests.map(donationRequestMapper::toResponseDTO);
	}

	@Override
	public DonationSummaryResponseDTO getDonationSummary() {

		Employee employee = loggedInUserService.getCurrentEmployee();

		LocalDate today = LocalDate.now();

		LocalDate monthStart = today.withDayOfMonth(1);
		LocalDate monthEnd = today.withDayOfMonth(today.lengthOfMonth());

		BigDecimal currentDonationAmount = donationRequestRepository.getCurrentMonthDonationAmount(
				employee.getEmployeeId(), monthStart, monthEnd, DonationStatus.INITIALIZED, DonationType.ONE_TIME,
				DonationType.RECURRING);

		BigDecimal basicSalary = employee.getBasicSalary();

		BigDecimal minimumSalaryReserve = BigDecimal.valueOf(5000);

		BigDecimal eligibleDonationAmount = basicSalary.subtract(minimumSalaryReserve).subtract(currentDonationAmount);

		DonationSummaryResponseDTO response = new DonationSummaryResponseDTO();

		response.setBasicSalary(basicSalary);
		response.setCurrentDonationAmount(currentDonationAmount);
		response.setEligibleDonationAmount(eligibleDonationAmount);

		return response;
	}

	@Override
	public DonationRequestResponseDTO cancelDonationRequest(String donationRequestId) {

		DonationRequest donationRequest = donationRequestRepository.findById(donationRequestId).orElseThrow(
				() -> new ResourceNotFoundException("Donation Request not found for Id : " + donationRequestId));
		if (donationRequest.getDonationStatus() == DonationStatus.CANCELLED) {
			throw new BusinessValidationException("Donation Request Already Cancelled");
		}
		ApplicationUser user = loggedInUserService.getCurrentApplicationUser();

		if (!loggedInUserService.isAdmin()) {
			if (!donationRequest.getEmployee().getEmployeeId()
					.equals(loggedInUserService.getCurrentEmployee().getEmployeeId())) {
				throw new AccessDeniedException("Access Denied");
			}

		}

		donationRequest.setDonationStatus(DonationStatus.CANCELLED);
//		donationRequest.setActive(false);
		donationRequestRepository.save(donationRequest);
		return donationRequestMapper.toResponseDTO(donationRequest);

//		if (user.getRole().getRoleName().equals(RoleType.ADMIN.name())) {
//			donationRequest.setDonationStatus(DonationStatus.CANCELLED);
//			donationRequest.setActive(false);
//			donationRequestRepository.save(donationRequest);
//			return donationRequestMapper.toResponseDTO(donationRequest);
//		}
//		if (!donationRequest.getEmployee().getEmployeeId()
//				.equals(loggedInUserService.getCurrentEmployee().getEmployeeId())) {
//			throw new BusinessValidationException("Access Denied");
//		} else {
//			donationRequest.setDonationStatus(DonationStatus.CANCELLED);
//			donationRequest.setActive(false);
//			donationRequestRepository.save(donationRequest);
//			return donationRequestMapper.toResponseDTO(donationRequest);
//		}

	}

}
