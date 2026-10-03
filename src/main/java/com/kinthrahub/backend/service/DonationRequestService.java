package com.kinthrahub.backend.service;

import org.springframework.data.domain.Page;

import com.kinthrahub.backend.dto.request.DonationRequestDTO;
import com.kinthrahub.backend.dto.response.DonationRequestResponseDTO;
import com.kinthrahub.backend.dto.response.DonationSummaryResponseDTO;
import com.kinthrahub.backend.enums.DonationStatus;
import com.kinthrahub.backend.enums.DonationType;

public interface DonationRequestService {
	
	DonationRequestResponseDTO addDonationRequest(DonationRequestDTO request);

	DonationRequestResponseDTO getDonationRequestById(String donationRequestId);

	Page<DonationRequestResponseDTO> getAllDonationRequests(
	        boolean isActive,
	        int page,
	        int size,
	        DonationStatus donationStatus,
	        DonationType donationType,
	        boolean myDonations);

	Page<DonationRequestResponseDTO> searchDonationRequests(
	        String search,
	        boolean isActive,
	        int page,
	        int size,
	        DonationStatus donationStatus,
	        DonationType donationType,
	        boolean myDonations);

	DonationRequestResponseDTO cancelDonationRequest(String donationRequestId);
	
	DonationSummaryResponseDTO getDonationSummary();

}
