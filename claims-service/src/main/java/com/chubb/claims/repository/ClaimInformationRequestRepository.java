package com.chubb.claims.repository;

import com.chubb.claims.entity.ClaimInformationRequest;
import com.chubb.claims.entity.enums.InformationRequestStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimInformationRequestRepository
        extends JpaRepository<ClaimInformationRequest, Long> {

    List<ClaimInformationRequest>
    findByClaimIdOrderByRequestedAtDesc(Long claimId);

    List<ClaimInformationRequest>
    findByClaimIdAndStatus(
            Long claimId,
            InformationRequestStatus status
    );
}