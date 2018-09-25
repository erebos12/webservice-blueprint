package com.alex.ws.infrastructure;


import com.alex.ws.domain.workflow.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowRepository extends JpaRepository<Workflow, Integer> {
}