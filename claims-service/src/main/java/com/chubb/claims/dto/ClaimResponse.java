package com.chubb.claims.dto;

import com.chubb.claims.entity.enums.ClaimStatus;
import com.chubb.claims.entity.enums.ClaimType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClaimResponse(

        Long id,
        String claimNumber,
        Long claimantId,
        ClaimType claimType,
        ClaimStatus status,
        String market,
        LocalDate incidentDate,
        String description,
        BigDecimal estimatedAmount,
        String currency,
        LocalDateTime createdAt

) {}