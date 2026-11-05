package com.chubb.claims.repository;

import com.chubb.claims.entity.ClaimAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface ClaimAssessmentRepository
        extends JpaRepository<ClaimAssessment, Long> {

    Optional<ClaimAssessment> findByClaimId(Long claimId);
}
