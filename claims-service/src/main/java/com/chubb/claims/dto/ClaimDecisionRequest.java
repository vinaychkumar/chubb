
package com.chubb.claims.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ClaimDecisionRequest(

        @NotBlank
        String decision,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal assessedAmount,

        @NotBlank
        @Size(max = 2000)
        String remarks

) {}
