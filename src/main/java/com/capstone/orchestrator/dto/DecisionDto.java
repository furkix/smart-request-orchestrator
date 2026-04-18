package com.capstone.orchestrator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DecisionDto {
    @NotBlank
    private String decision; // approve | reject
    private String note;
    private String approver;
}
