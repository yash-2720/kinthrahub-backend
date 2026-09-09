package com.kinthrahub.backend.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DonationSummaryResponseDTO {

	private BigDecimal basicSalary;

	private BigDecimal currentDonationAmount;

	private BigDecimal eligibleDonationAmount;
}