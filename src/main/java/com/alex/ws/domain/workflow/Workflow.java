package com.alex.ws.domain.workflow;

import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "workflow_log")
public class Workflow {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name = "wrk_id", nullable = false)
    public Integer wrk_id;

    @Column(name = "wrk_clt_id", nullable = false)
    public Integer wrk_clt_id;

    @Column(name = "wrk_csg_id", nullable = false)
    public Integer wrk_csg_id;

    @Column(name = "wrk_type_cd", nullable = false)
    public String wrk_type_cd;

    @Column(name = "wrk_def_type_cd", nullable = false)
    public Integer wrk_def_type_cd;

    @Column(name = "wrk_current_step", nullable = false)
    public Integer wrk_current_step;

    @Column(name = "wrk_current_status", nullable = false)
    public Integer wrk_current_status;

    @Column(name = "wrk_pdi_job_id")
    public Integer wrk_pdi_job_id;

    @Column(name = "wrk_strt_dt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date wrk_strt_dt;

    @Column(name = "wrk_end_dt")
    @Temporal(TemporalType.TIMESTAMP)
    public Date wrk_end_dt;

    @Column(name = "wrk_success_quote")
    public Double wrk_success_quote;

    @Column(name = "wrk_activating_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date wrk_activating_time;
}
