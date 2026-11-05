package com.chubb.claims.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "claim_assessments")
@Getter
@Setter
@NoArgsConstructor
public class ClaimAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", nullable = false, unique = true)
    private Claim claim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessed_by")
    private AppUser assessedBy;

    @Column(precision = 15, scale = 2)
    private BigDecimal assessedAmount;

    private String decision;

    @Column(length = 2000)
    private String remarks;

    private LocalDateTime assessedAt;

    @PrePersist
    protected void onCreate() {
        assessedAt = LocalDateTime.now();
    }
    

public void setClaim(Claim claim) {
    this.claim = claim;
}

public void setAssessedBy(AppUser assessedBy) {
    this.assessedBy = assessedBy;
}

public void setAssessedAmount(BigDecimal assessedAmount) {
    this.assessedAmount = assessedAmount;
}

public void setDecision(String decision) {
    this.decision = decision;
}

public void setRemarks(String remarks) {
    this.remarks = remarks;
}

}