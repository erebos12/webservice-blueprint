package com.bisnode.bhc.infrastructure;


import com.bisnode.bhc.domain.workflow.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowRepository extends JpaRepository<Workflow, Integer> {
}