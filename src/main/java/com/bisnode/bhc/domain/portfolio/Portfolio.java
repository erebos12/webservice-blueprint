package com.bisnode.bhc.domain.portfolio;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "portfolio")
public class Portfolio {
    
    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name = "pfl_id", nullable = false)
    public Integer pfl_id;

    @Column(name = "pfl_csg_id", nullable = true)
    public Integer pfl_csg_id;

    @Column(name = "pfl_wrk_id", nullable = true)
    public Integer pfl_wrk_id;

    @Column(name = "pfl_cust_identifier", nullable = false)
    public String pfl_cust_identifier;

    @Column(name = "pfl_ext_identifier", nullable = false)
    public Integer pfl_ext_identifier;

    @Column(name = "pfl_dtt_id", nullable = false)
    public Integer pfl_dtt_id;

    @Column(name = "pfl_country_iso2", nullable = false)
    public String pfl_country_iso2;

    @Column(name = "pfl_strt_dt", nullable = false)
    public Date pfl_strt_dt;

    @Column(name = "pfl_end_dt", nullable = true)
    public Date pfl_end_dt;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
