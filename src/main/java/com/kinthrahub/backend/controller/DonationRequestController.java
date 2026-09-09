package com.kinthrahub.backend.controller;

import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kinthrahub.backend.dto.request.DonationRequestDTO;
import com.kinthrahub.backend.dto.response.DonationRequestResponseDTO;
import com.kinthrahub.backend.dto.response.DonationSummaryResponseDTO;
import com.kinthrahub.backend.service.DonationRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/donationRequest")
public class DonationRequestController {

	private DonationRequestService donationRequestService;

	public DonationRequestController(DonationRequestService donationRequestService) {
		this.donationRequestService = donationRequestService;
	}

	@PostMapping("/createDonationRequest")
	DonationRequestResponseDTO addDonationRequest(@RequestBody @Valid DonationRequestDTO request) {
		return donationRequestService.addDonationRequest(request);
	}

	@GetMapping("/getDonationRequestById/{id}")
	public DonationRequestResponseDTO getDonationRequestById(@PathVariable String id) {
		return donationRequestService.getDonationRequestById(id);
	}

	@GetMapping("/getAllDonationRequests")
	public Page<DonationRequestResponseDTO> getAllDonationRequests(
			@RequestParam(defaultValue = "true") boolean isActive, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		return donationRequestService.getAllDonationRequests(isActive, page, size);
	}

	@GetMapping("/search")
	public Page<DonationRequestResponseDTO> searchDonationRequests(@RequestParam(required = false) String search,
			@RequestParam(defaultValue = "true") boolean isActive, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		return donationRequestService.searchDonationRequests(search, isActive, page, size);
	}

	@DeleteMapping("/cancelDonationRequest/{id}")
	public DonationRequestResponseDTO cancelDonationRequest(@PathVariable String id) {
		return donationRequestService.cancelDonationRequest(id);
	}

	@GetMapping("/getDonationSummary")
	public DonationSummaryResponseDTO getDonationSummary() {

		return donationRequestService.getDonationSummary();
	}

}
