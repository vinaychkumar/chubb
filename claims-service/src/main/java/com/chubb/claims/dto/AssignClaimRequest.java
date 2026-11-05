
package com.chubb.claims.dto;

import jakarta.validation.constraints.NotNull;

public record AssignClaimRequest(
        @NotNull Long officerId
) {}
