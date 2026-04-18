package com.capstone.orchestrator.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApprovalEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requestId;
    private String approver;
    private String decision; // approve/reject
    private String note;
    private Instant decidedAt;

    @PrePersist
    public void onCreate() {
        if (decidedAt == null) decidedAt = Instant.now();
    }
}
