package com.chubb.claims.dto;

import com.chubb.claims.entity.enums.ClaimType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClaimRequest(

        @NotNull
        Long claimantId,

        @NotNull
        ClaimType claimType,

        @NotBlank
        String market,

        @NotNull
        LocalDate incidentDate,

        @NotBlank
        @Size(max = 2000)
        String description,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal estimatedAmount,

        @NotBlank
        String currency

) {}