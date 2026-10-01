package com.kinthrahub.backend.specification;

import org.springframework.data.jpa.domain.Specification;

import com.kinthrahub.backend.entity.DonationPlan;
import com.kinthrahub.backend.entity.Hospital;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class HospitalSpecification {

	public static Specification<Hospital> search(String search) {

		return (root, query, criteriaBuilder) -> {

			if (search == null || search.isBlank()) {
				return criteriaBuilder.conjunction();
			}

			String pattern = "%" + search.toLowerCase() + "%";

			// Subquery to search DonationPlan
			Subquery<Integer> donationPlanSubquery = query.subquery(Integer.class);

			Root<DonationPlan> donationPlanRoot = donationPlanSubquery.from(DonationPlan.class);

			donationPlanSubquery.select(criteriaBuilder.literal(1))
					.where(criteriaBuilder.equal(donationPlanRoot.get("hospital"), root), criteriaBuilder.or(
							criteriaBuilder.like(criteriaBuilder.lower(donationPlanRoot.get("donationName")), pattern),
							criteriaBuilder.like(criteriaBuilder.lower(donationPlanRoot.get("donationDescription")),
									pattern)));

			return criteriaBuilder.or(

					criteriaBuilder.like(criteriaBuilder.lower(root.get("hospitalId")), pattern),
					criteriaBuilder.like(criteriaBuilder.lower(root.get("hospitalDescription")), pattern),

					criteriaBuilder.like(criteriaBuilder.lower(root.get("hospitalName")), pattern),
					
					// Search Donation Plan
	                criteriaBuilder.exists(donationPlanSubquery));
		};
//		criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeNumber")), pattern),
//		criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeEmail")), pattern));
	}

	public static Specification<Hospital> isActive(boolean isActive) {

		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive);

	}

}