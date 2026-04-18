package com.capstone.orchestrator.service;

import com.capstone.orchestrator.dto.DecisionDto;
import com.capstone.orchestrator.dto.IncomingDto;
import com.capstone.orchestrator.model.ApprovalEntity;
import com.capstone.orchestrator.model.RequestEntity;
import com.capstone.orchestrator.repo.ApprovalRepository;
import com.capstone.orchestrator.repo.RequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final ApprovalRepository approvalRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public RequestEntity createFromIncoming(IncomingDto dto) throws JsonProcessingException {
        String category = "general";
        String priority = "normal";

        if (dto.getExtracted() != null) {
            Object c = dto.getExtracted().get("category");
            if (c != null) category = c.toString();
            Object p = dto.getExtracted().get("priority");
            if (p != null) priority = p.toString();
        }

        RequestEntity r = RequestEntity.builder()
                .source(dto.getSource())
                .title(dto.getSubject())
                .category(category)
                .priority(priority)
                .status("pending")
                .slaDueAt(Instant.now().plus(2, ChronoUnit.DAYS))
                .createdAt(Instant.now())
                .extractedJson(dto.getExtracted() != null ? objectMapper.writeValueAsString(dto.getExtracted()) : null)
                .build();

        return requestRepository.save(r);
    }

    public RequestEntity decide(Long id, DecisionDto d) {
        RequestEntity r = requestRepository.findById(id).orElseThrow();

        String status = "rejected";
        if ("approve".equalsIgnoreCase(d.getDecision())) status = "approved";
        if ("reject".equalsIgnoreCase(d.getDecision())) status = "rejected";

        r.setStatus(status);
        requestRepository.save(r);

        ApprovalEntity a = ApprovalEntity.builder()
                .requestId(r.getId())
                .approver(d.getApprover())
                .decision(status.equals("approved") ? "approve" : "reject")
                .note(d.getNote())
                .build();
        approvalRepository.save(a);

        return r;
    }

    public List<RequestEntity> list(String status, String category) {
        if (status != null) return requestRepository.findByStatus(status);
        if (category != null) return requestRepository.findByCategory(category);
        return requestRepository.findAll();
    }
}
