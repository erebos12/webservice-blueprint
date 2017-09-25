package com.bisnode.bhc.application;

import com.bisnode.bhc.domain.workflow.Workflow;
import com.bisnode.bhc.infrastructure.TableUpserter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WorkflowDbOperator {

    @Autowired
    private TableUpserter tableUpserter;

    public void insertWorkflowFor(Integer wrk_csg_id) {
        Workflow workflow = new Workflow();
        workflow.wrk_clt_id = 1;
        workflow.wrk_csg_id = wrk_csg_id;
        workflow.wrk_type_cd = "1";
        workflow.wrk_def_type_cd = 1;
        workflow.wrk_current_step = 0;
        workflow.wrk_current_status = "0";
        workflow.wrk_strt_dt = new Date();
        tableUpserter.insert(workflow);
    }
}
