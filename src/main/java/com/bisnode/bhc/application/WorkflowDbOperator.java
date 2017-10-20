package com.bisnode.bhc.application;

import com.bisnode.bhc.domain.workflow.Workflow;
import com.bisnode.bhc.infrastructure.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WorkflowDbOperator {

    @Autowired
    private WorkflowRepository workflowRepository;

    public void insertWorkflowFor(final Integer wrk_csg_id) {
        Workflow workflow = new Workflow();
        workflow.wrk_clt_id = 1;
        workflow.wrk_csg_id = wrk_csg_id;
        workflow.wrk_type_cd = "3";
        workflow.wrk_def_type_cd = 1;
        workflow.wrk_current_step = 0;
        workflow.wrk_current_status = 0;
        workflow.wrk_strt_dt = new Date();
        workflowRepository.save(workflow);
    }
}
