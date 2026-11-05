package com.chubb.claims.controller;

import com.chubb.claims.dto.ClaimRequest;
import com.chubb.claims.dto.ClaimResponse;
import com.chubb.claims.service.ClaimService;
import com.chubb.claims.dto.AssignClaimRequest;
import com.chubb.claims.dto.AdditionalInfoRequest;
import com.chubb.claims.dto.ClaimDecisionRequest;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "http://localhost:4200")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    // Submit a new claim
    @PostMapping
    public ResponseEntity<ClaimResponse> createClaim(
            @Valid @RequestBody ClaimRequest request) {

        ClaimResponse response =
                claimService.createClaim(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get claims belonging to a claimant
    @GetMapping("/claimant/{claimantId}")
    public ResponseEntity<List<ClaimResponse>> getClaimsByClaimant(
            @PathVariable Long claimantId) {

        return ResponseEntity.ok(
                claimService.getClaimsByClaimant(claimantId)
        );
    }

    // Track a claim using its claim number
    @GetMapping("/{claimNumber}")
    public ResponseEntity<ClaimResponse> getClaimByNumber(
            @PathVariable String claimNumber) {

        return ResponseEntity.ok(
                claimService.getClaimByNumber(claimNumber)
        );
    }
    
    
 // View all incoming claims
    @GetMapping("/incoming")
    public ResponseEntity<List<ClaimResponse>> getIncomingClaims() {

        return ResponseEntity.ok(
                claimService.getIncomingClaims()
        );
    }

    // View claims assigned to an officer
    @GetMapping("/officer/{officerId}")
    public ResponseEntity<List<ClaimResponse>> getAssignedClaims(
            @PathVariable Long officerId) {

        return ResponseEntity.ok(
                claimService.getAssignedClaims(officerId)
        );
    }

    // Assign claim to an officer
    @PutMapping("/{claimNumber}/assign")
    public ResponseEntity<ClaimResponse> assignClaim(
            @PathVariable String claimNumber,
            @Valid @RequestBody AssignClaimRequest request) {

        return ResponseEntity.ok(
                claimService.assignClaim(claimNumber, request)
        );
    }

    // Request additional information
    @PatchMapping("/{claimNumber}/request-info")
    public ResponseEntity<ClaimResponse> requestAdditionalInfo(
            @PathVariable String claimNumber,
            @RequestParam Long officerId,
            @Valid @RequestBody AdditionalInfoRequest request) {

        return ResponseEntity.ok(
                claimService.requestAdditionalInfo(
                        claimNumber, officerId, request
                )
        );
    }

    // Approve or reject claim
    @PatchMapping("/{claimNumber}/decision")
    public ResponseEntity<ClaimResponse> decideClaim(
            @PathVariable String claimNumber,
            @RequestParam Long officerId,
            @Valid @RequestBody ClaimDecisionRequest request) {

        return ResponseEntity.ok(
                claimService.decideClaim(
                        claimNumber, officerId, request
                )
        );
    }

    // Settle approved claim
    @PatchMapping("/{claimNumber}/settle")
    public ResponseEntity<ClaimResponse> settleClaim(
            @PathVariable String claimNumber,
            @RequestParam Long officerId) {

        return ResponseEntity.ok(
                claimService.settleClaim(claimNumber, officerId)
        );
    }
}