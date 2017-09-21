package com.bisnode.bhc.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name = "pfl_id")
    public int pfl_id;

    @Column(name = "pfl_csg_id")
    public int pfl_csg_id;

    @Column(name = "pfl_wrk_id")
    public int pfl_wrk_id;

    @Column(name = "pfl_cust_identifier")
    public String pfl_cust_identifier;

    @Column(name = "pfl_ext_identifier")
    public int pfl_ext_identifier;

    @Column(name = "pfl_dtt_id")
    public int pfl_dtt_id;

    @Column(name = "pfl_country_iso2")
    public String pfl_country_iso2;

    @Column(name = "pfl_strt_dt")
    public Date pfl_strt_dt;

    @Column(name = "pfl_end_dt")
    public Date pfl_end_dt;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
