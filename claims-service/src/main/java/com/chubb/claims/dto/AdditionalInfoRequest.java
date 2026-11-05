
package com.chubb.claims.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdditionalInfoRequest(
        @NotBlank
        @Size(max = 1000)
        String comments
) {}
