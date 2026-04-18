package com.capstone.orchestrator.web;

import com.capstone.orchestrator.dto.DecisionDto;
import com.capstone.orchestrator.dto.IncomingDto;
import com.capstone.orchestrator.model.RequestEntity;
import com.capstone.orchestrator.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/webhooks/powerautomate")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody IncomingDto dto) throws JsonProcessingException {
        RequestEntity r = requestService.createFromIncoming(dto);
        Map<String, Object> resp = new HashMap<>();
        resp.put("ok", true);
        resp.put("id", r.getId());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/requests/{id}/approve")
    public ResponseEntity<Map<String, Object>> approve(@PathVariable Long id, @Valid @RequestBody DecisionDto d) {
        RequestEntity r = requestService.decide(id, d);
        Map<String, Object> resp = new HashMap<>();
        resp.put("ok", true);
        resp.put("status", r.getStatus());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/requests")
    public List<RequestEntity> list(@RequestParam(required = false) String status,
                                    @RequestParam(required = false) String category) {
        return requestService.list(status, category);
    }
}
