package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.workflow.Workflow;
import com.bisnode.bhc.utils.H2DbInitializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class WorkflowDbOperator {

    public String persistence_unit;
    private TableUpserter tableUpserter;

    @Autowired
    public WorkflowDbOperator(CfgParams cfgParams) throws SQLException, IOException {
        if ("prod".equalsIgnoreCase(cfgParams.mode)) {
            persistence_unit = "portfolio_prod";
        } else {
            persistence_unit = "portfolio_test";
            H2DbInitializer.initializeH2();
        }
        tableUpserter = new TableUpserter(persistence_unit);
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
