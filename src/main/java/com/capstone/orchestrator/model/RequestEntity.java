package com.capstone.orchestrator.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RequestEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;
    private String title;
    private String category;
    private String priority;
    private String status; // pending, approved, rejected

    private Instant slaDueAt;
    private Instant createdAt;

    @Lob
    private String extractedJson;

    @PrePersist
    public void onCreate() {
        if (createdAt == null) createdAt = Instant.now();
        if (status == null) status = "pending";
    }
}
