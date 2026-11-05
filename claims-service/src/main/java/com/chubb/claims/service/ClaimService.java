package com.chubb.claims.service;

import com.chubb.claims.entity.ClaimAssessment;
import com.chubb.claims.entity.enums.UserRole;
import com.chubb.claims.dto.AssignClaimRequest;
import com.chubb.claims.dto.AdditionalInfoRequest;
import com.chubb.claims.dto.ClaimDecisionRequest;
import com.chubb.claims.repository.ClaimAssessmentRepository;
import com.chubb.claims.dto.ClaimRequest;
import com.chubb.claims.dto.ClaimResponse;
import com.chubb.claims.entity.AppUser;
import com.chubb.claims.entity.Claim;
import com.chubb.claims.entity.ClaimHistory;
import com.chubb.claims.entity.enums.ClaimStatus;
import com.chubb.claims.repository.ClaimRepository;
import com.chubb.claims.repository.ClaimHistoryRepository;
import com.chubb.claims.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;


@Service
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;
    private final ClaimHistoryRepository historyRepository;
    private final ClaimAssessmentRepository assessmentRepository;

    public ClaimService(
            ClaimRepository claimRepository,
            UserRepository userRepository,
            ClaimHistoryRepository historyRepository,
            ClaimAssessmentRepository assessmentRepository) {
        this.claimRepository = claimRepository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.assessmentRepository = assessmentRepository;
    }

    @Transactional
    public ClaimResponse createClaim(ClaimRequest request) {

        // Find claimant
        AppUser claimant = userRepository.findById(
                request.claimantId()
        ).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Claimant not found"
                )
        );

        // Verify user role
        if (claimant.getRole() != UserRole.CLAIMANT) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only claimants can submit claims"
            );
        }

        // Validate incident date
        if (request.incidentDate().isAfter(
                java.time.LocalDate.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Incident date cannot be in the future"
            );
        }

        // Create claim
        Claim claim = new Claim();

        claim.setClaimNumber(generateClaimNumber());
        claim.setClaimant(claimant);
        System.out.println("Claimant before save: " + claim.getClaimant());
        System.out.println("Claimant ID: " + claim.getClaimant().getId());
        claim.setClaimType(request.claimType());
        claim.setMarket(request.market());
        claim.setIncidentDate(request.incidentDate());
        claim.setDescription(request.description());
        claim.setEstimatedAmount(request.estimatedAmount());
        claim.setCurrency(request.currency());
        claim.setStatus(ClaimStatus.SUBMITTED);

        Claim savedClaim = claimRepository.save(claim);
        System.out.println("Claimant after save: " + savedClaim.getClaimant());
        // Record initial claim history
        ClaimHistory history = new ClaimHistory();

        history.setClaim(savedClaim);
        history.setPreviousStatus(null);
        history.setNewStatus(ClaimStatus.SUBMITTED);
        history.setComments("Claim submitted successfully");

        historyRepository.save(history);

        return mapToResponse(savedClaim);
    }

    @Transactional(readOnly = true)
    public List<ClaimResponse> getClaimsByClaimant(Long claimantId) {

        return claimRepository.findByClaimantId(claimantId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    
    @Transactional(readOnly = true)
    public ClaimResponse getClaimByNumber(String claimNumber) {

        Claim claim = claimRepository.findByClaimNumber(claimNumber)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Claim not found"
                        )
                );

        return mapToResponse(claim);
    }

   

    @Transactional(readOnly = true)
    public List<ClaimResponse> getIncomingClaims() {

        return claimRepository.findByStatus(ClaimStatus.SUBMITTED)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    
@Transactional(readOnly = true)
public List<ClaimResponse> getAssignedClaims(Long officerId) {

    AppUser officer = userRepository.findById(officerId)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Officer not found"
            ));

    if (officer.getRole() != UserRole.CLAIMS_OFFICER) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "User is not a claims officer"
        );
    }

    return claimRepository.findByAssignedOfficerId(officerId)
            .stream()
            .map(this::mapToResponse)
            .toList();
}


@Transactional
public ClaimResponse assignClaim(
        String claimNumber,
        AssignClaimRequest request) {

    Claim claim = findClaim(claimNumber);
    AppUser officer = userRepository.findById(request.officerId())
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Officer not found"
            ));
    if (officer.getRole() != UserRole.CLAIMS_OFFICER) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Only claims officers can be assigned"
        );
    }
    if (claim.getStatus() != ClaimStatus.SUBMITTED) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Only submitted claims can be assigned"
        );
    }
    ClaimStatus previousStatus = claim.getStatus();

    claim.setAssignedOfficer(officer);
    claim.setStatus(ClaimStatus.UNDER_REVIEW);
    Claim savedClaim = claimRepository.save(claim);
    saveHistory(
            savedClaim,
            previousStatus,
            ClaimStatus.UNDER_REVIEW,
            "Claim assigned to claims officer",
            officer
    );

    return mapToResponse(savedClaim);
}


@Transactional
public ClaimResponse requestAdditionalInfo(
        String claimNumber,
        Long officerId,
        AdditionalInfoRequest request) {

    Claim claim = findClaim(claimNumber);

    AppUser officer = findOfficer(officerId);

    verifyAssignedOfficer(claim, officer);

    if (claim.getStatus() != ClaimStatus.UNDER_REVIEW) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Claim is not currently under review"
        );
    }

    ClaimStatus previousStatus = claim.getStatus();

    claim.setStatus(ClaimStatus.ADDITIONAL_INFO_REQUIRED);

    Claim savedClaim = claimRepository.save(claim);

    saveHistory(
            savedClaim,
            previousStatus,
            ClaimStatus.ADDITIONAL_INFO_REQUIRED,
            request.comments(),
            officer
    );

    return mapToResponse(savedClaim);
}


@Transactional
public ClaimResponse decideClaim(
        String claimNumber,
        Long officerId,
        ClaimDecisionRequest request) {

    Claim claim = findClaim(claimNumber);

    AppUser officer = findOfficer(officerId);

    verifyAssignedOfficer(claim, officer);

    if (claim.getStatus() != ClaimStatus.UNDER_REVIEW) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Claim must be under review before a decision"
        );
    }

    String decision = request.decision().trim().toUpperCase();

    ClaimStatus newStatus;

    if ("APPROVED".equals(decision)) {
        newStatus = ClaimStatus.APPROVED;
    } else if ("REJECTED".equals(decision)) {
        newStatus = ClaimStatus.REJECTED;
    } else {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Decision must be APPROVED or REJECTED"
        );
    }

    if (assessmentRepository.findByClaimId(claim.getId()).isPresent()) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "An assessment already exists for this claim"
        );
    }

    ClaimAssessment assessment = new ClaimAssessment();
    assessment.setClaim(claim);
    assessment.setAssessedBy(officer);
    assessment.setAssessedAmount(request.assessedAmount());
    assessment.setDecision(decision);
    assessment.setRemarks(request.remarks());

    assessmentRepository.save(assessment);

    ClaimStatus previousStatus = claim.getStatus();

    claim.setStatus(newStatus);

    Claim savedClaim = claimRepository.save(claim);

    saveHistory(
            savedClaim,
            previousStatus,
            newStatus,
            request.remarks(),
            officer
    );

    return mapToResponse(savedClaim);
}



    private String generateClaimNumber() {
        return "CHB-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    private ClaimResponse mapToResponse(Claim claim) {

        return new ClaimResponse(
                claim.getId(),
                claim.getClaimNumber(),
                claim.getClaimant().getId(),
                claim.getClaimType(),
                claim.getStatus(),
                claim.getMarket(),
                claim.getIncidentDate(),
                claim.getDescription(),
                claim.getEstimatedAmount(),
                claim.getCurrency(),
                claim.getCreatedAt()
        );
    }
    
    private Claim findClaim(String claimNumber) {

        return claimRepository.findByClaimNumber(claimNumber)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Claim not found"
                ));
    }  
    
    private AppUser findOfficer(Long officerId) {

        AppUser officer = userRepository.findById(officerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Officer not found"
                ));

        if (officer.getRole() != UserRole.CLAIMS_OFFICER) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "User is not a claims officer"
            );
        }

        return officer;
    }
    
    private void verifyAssignedOfficer(
            Claim claim,
            AppUser officer) {

        if (claim.getAssignedOfficer() == null ||
                !claim.getAssignedOfficer().getId()
                        .equals(officer.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Claim is not assigned to this officer"
            );
        }
    }
    
    private void saveHistory(
            Claim claim,
            ClaimStatus previousStatus,
            ClaimStatus newStatus,
            String comments,
            AppUser officer) {

        ClaimHistory history = new ClaimHistory();

        history.setClaim(claim);
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(newStatus);
        history.setComments(comments);
        history.setChangedBy(officer);

        historyRepository.save(history);
    }
    

@Transactional
public ClaimResponse settleClaim(
        String claimNumber,
        Long officerId) {

    Claim claim = findClaim(claimNumber);

    AppUser officer = findOfficer(officerId);

    verifyAssignedOfficer(claim, officer);

    if (claim.getStatus() != ClaimStatus.APPROVED) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Only approved claims can be settled"
        );
    }

    ClaimStatus previousStatus = claim.getStatus();

    claim.setStatus(ClaimStatus.SETTLED);

    Claim savedClaim = claimRepository.save(claim);

    saveHistory(
            savedClaim,
            previousStatus,
            ClaimStatus.SETTLED,
            "Claim settled",
            officer
    );

    return mapToResponse(savedClaim);
}

    
}