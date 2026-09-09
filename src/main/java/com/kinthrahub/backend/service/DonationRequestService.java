package com.kinthrahub.backend.service;

import org.springframework.data.domain.Page;

import com.kinthrahub.backend.dto.request.DonationRequestDTO;
import com.kinthrahub.backend.dto.response.DonationRequestResponseDTO;
import com.kinthrahub.backend.dto.response.DonationSummaryResponseDTO;

public interface DonationRequestService {
	
	DonationRequestResponseDTO addDonationRequest(DonationRequestDTO request);

	DonationRequestResponseDTO getDonationRequestById(String donationRequestId);

	Page<DonationRequestResponseDTO> getAllDonationRequests(
	        boolean isActive,
	        int page,
	        int size);

	Page<DonationRequestResponseDTO> searchDonationRequests(
	        String search,
	        boolean isActive,
	        int page,
	        int size);

	DonationRequestResponseDTO cancelDonationRequest(String donationRequestId);
	
	DonationSummaryResponseDTO getDonationSummary();

}
