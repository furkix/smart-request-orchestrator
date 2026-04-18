package com.capstone.orchestrator.repo;

import com.capstone.orchestrator.model.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findByStatus(String status);
    List<RequestEntity> findByCategory(String category);
}
