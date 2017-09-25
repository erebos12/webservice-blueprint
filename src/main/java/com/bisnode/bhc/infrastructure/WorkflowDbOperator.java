package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.workflow.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@Component
public class WorkflowDbOperator {

    private TableUpserter tableUpserter;

    @Autowired
    public WorkflowDbOperator(TableUpserter tableUpserter) throws SQLException, IOException {
        this.tableUpserter = tableUpserter;
    }

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
