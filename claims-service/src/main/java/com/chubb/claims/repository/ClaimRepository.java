package com.chubb.claims.repository;

import com.chubb.claims.entity.Claim;
import com.chubb.claims.entity.enums.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClaimRepository
        extends JpaRepository<Claim, Long> {

    Optional<Claim> findByClaimNumber(String claimNumber);

    boolean existsByClaimNumber(String claimNumber);

    List<Claim> findByClaimantId(Long claimantId);

    List<Claim> findByAssignedOfficerId(Long officerId);

    List<Claim> findByStatus(ClaimStatus status);
}