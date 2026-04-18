package com.capstone.orchestrator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class IncomingDto {
    @NotBlank
    private String source;
    @NotBlank
    private String subject;
    private String body;
    private Map<String, Object> extracted;
    private List<String> attachments;
}
