package com.capstone.orchestrator.repo;

import com.capstone.orchestrator.model.ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
    List<ApprovalEntity> findByRequestId(Long requestId);
}
