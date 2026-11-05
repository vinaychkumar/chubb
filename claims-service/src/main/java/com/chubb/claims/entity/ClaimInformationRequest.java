package com.chubb.claims.entity;

import com.chubb.claims.entity.enums.InformationRequestStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "claim_information_requests")
@Getter
@Setter
@NoArgsConstructor
public class ClaimInformationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", nullable = false)
    private Claim claim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by", nullable = false)
    private AppUser requestedBy;

    @Column(nullable = false, length = 1000)
    private String requestMessage;

    @Column(length = 2000)
    private String responseMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InformationRequestStatus status;

    private LocalDateTime requestedAt;

    private LocalDateTime respondedAt;

    @PrePersist
    protected void onCreate() {
        requestedAt = LocalDateTime.now();
        status = InformationRequestStatus.OPEN;
    }
}