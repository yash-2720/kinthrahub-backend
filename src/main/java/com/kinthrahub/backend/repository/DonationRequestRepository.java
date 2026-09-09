package com.kinthrahub.backend.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kinthrahub.backend.entity.DonationRequest;
import com.kinthrahub.backend.entity.Employee;
import com.kinthrahub.backend.enums.DonationStatus;
import com.kinthrahub.backend.enums.DonationType;

@Repository
public interface DonationRequestRepository
		extends JpaRepository<DonationRequest, String>, JpaSpecificationExecutor<DonationRequest> {

	boolean existsByEmployeeEmployeeId(String employeeId);

	List<DonationRequest> findByDonationStatusAndIsActive(DonationStatus donationStatus, boolean isActive);

	Page<DonationRequest> findByEmployee(Employee employee, Pageable pageable);

	DonationRequest findByEmployee(Employee employee);

	Optional<DonationRequest> findByDonationRequestIdAndEmployeeEmployeeId(String donationRequestId, String employeeId);

	@Query("""
			    SELECT
			        COUNT(d) AS activeDonations,
			        SUM(d.donationAmount) AS totalDonationAmount
			    FROM DonationRequest d
			    WHERE d.employee = :employee
			    AND d.isActive = true
			""")
	MyDonationSummaryProjection getMyDonationSummary(@Param("employee") Employee employee);
	
	@Query("""
		    SELECT
		        COUNT(d) AS activeDonations,
		        SUM(d.donationAmount) AS totalDonationAmount
		    FROM DonationRequest d
		    WHERE d.isActive = true
		""")
	MyDonationSummaryProjection getDonationRequestSummary();
	
	
	
	
	
	@Query("""
			SELECT COALESCE(SUM(dr.donationAmount), 0)
			FROM DonationRequest dr
			WHERE dr.employee.employeeId = :employeeId
			AND dr.isActive = true
			AND dr.donationStatus = :donationStatus
			AND (
				(
					dr.donationType = :oneTime
					AND dr.donationStartDate BETWEEN :monthStart AND :monthEnd
				)
				OR
				(
					dr.donationType = :recurring
					AND dr.donationStartDate <= :monthEnd
					AND (
						dr.donationEndDate IS NULL
						OR dr.donationEndDate >= :monthStart
					)
				)
			)
		""")
	BigDecimal getCurrentMonthDonationAmount(
			@Param("employeeId") String employeeId,
			@Param("monthStart") LocalDate monthStart,
			@Param("monthEnd") LocalDate monthEnd,
			@Param("donationStatus") DonationStatus donationStatus,
			@Param("oneTime") DonationType oneTime,
			@Param("recurring") DonationType recurring);
}
