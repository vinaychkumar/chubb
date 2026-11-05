package com.chubb.claims.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClaimantInformationResponse(

    @NotBlank
    @Size(max = 2000)
    String responseMessage

) {}