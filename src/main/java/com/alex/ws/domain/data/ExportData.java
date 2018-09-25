package com.alex.ws.domain.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "view_export_data")
public class ExportData {

    @Id
    @Column(name = "duns_nbr")
    public String duns_nbr;

    @Column(name = "prim_nme")
    public String prim_nme;

    @Column(name = "pfl_csg_id")
    public Integer pfl_csg_id;

    @Column(name = "pfl_dtt_id", nullable = false)
    public Integer pfl_dtt_id;

    @Column(name = "pm_start", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date pm_start;

    @Column(name = "gdp_start", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date gdp_start;

    @Column(name = "adr_line")
    public String adr_line;

    @Column(name = "bus_regn_nbr")
    public String bus_regn_nbr;

    @Column(name = "regn_nbr_type_cd")
    public String regn_nbr_type_cd;

    @Column(name = "vat_number")
    public String vat_number;

    @Column(name = "crcy_cd")
    public String crcy_cd;

    @Column(name = "ctry_cd")
    public String ctry_cd;

    @Column(name = "fax_nbr")
    public String fax_nbr;

    @Column(name = "intl_dlng_cd")
    public String intl_dlng_cd;

    @Column(name = "legal_form")
    public String legal_form;

    @Column(name = "post_code")
    public String post_code;

    @Column(name = "post_town")
    public String post_town;

    @Column(name = "prim_geo_area")
    public String prim_geo_area;

    @Column(name = "tlcm_nbr")
    public String tlcm_nbr;

    @Column(name = "trdg_styl")
    public String trdg_styl;

    @Column(name = "adr_tenr_type_cd")
    public String adr_tenr_type_cd;

    @Column(name = "ceo_nme")
    public String ceo_nme;

    @Column(name = "ceo_titl")
    public String ceo_titl;

    @Column(name = "curr_cntl_yr")
    public String curr_cntl_yr;

    @Column(name = "empl_at_prim_adr")
    public String empl_at_prim_adr;

    @Column(name = "empl_at_prim_adr_estd_ind")
    public String empl_at_prim_adr_estd_ind;

    @Column(name = "empl_at_prim_adr_min_ind")
    public String empl_at_prim_adr_min_ind;

    @Column(name = "enq_duns")
    public String enq_duns;

    @Column(name = "incn_yr")
    public Integer incn_yr;

    @Column(name = "lcl_actv_cd")
    public String lcl_actv_cd;

    @Column(name = "lcl_actv_cd_type")
    public String lcl_actv_cd_type;

    @Column(name = "out_bus_ind")
    public String out_bus_ind;

    @Column(name = "prim_sic")
    public String prim_sic;

    @Column(name = "prin_nme")
    public String prin_nme;

    @Column(name = "prin_ttl")
    public String prin_ttl;

    @Column(name = "regn_type")
    public String regn_type;

    @Column(name = "strt_yr")
    public String strt_yr;

    @Column(name = "tot_empl")
    public Integer tot_empl;

    @Column(name = "emma_score")
    public String emma_score;

    @Column(name = "fail_scr_natl_pctg")
    public Integer fail_scr_natl_pctg;

    @Column(name = "fail_scr_natl_pctg_12mo")
    public Integer fail_scr_natl_pctg_12mo;

    @Column(name = "fail_scr_natl_pctg_3mo")
    public Integer fail_scr_natl_pctg_3mo;

    @Column(name = "fail_scr_natl_pctg_6mo")
    public Integer fail_scr_natl_pctg_6mo;

    @Column(name = "fail_scr_natl_pctg_9mo")
    public Integer fail_scr_natl_pctg_9mo;

    @Column(name = "max_cr")
    public Double max_cr;

    @Column(name = "max_cr_crcy_cd")
    public String max_cr_crcy_cd;

    @Column(name = "dnb_ratg_fin_str")
    public String dnb_ratg_fin_str;

    @Column(name = "dnb_ratg_risk_ind")
    public String dnb_ratg_risk_ind;

    @Column(name = "payd_norm")
    public Integer payd_norm;

    @Column(name = "payd_scr")
    public Integer payd_scr;

    @Column(name = "payd_3_mo_ago")
    public Integer payd_3_mo_ago;

    @Column(name = "clm_ind")
    public String clm_ind;

    @Column(name = "crim_ind")
    public String crim_ind;

    @Column(name = "dstr_ind")
    public String dstr_ind;

    @Column(name = "finl_embt_ind")
    public String finl_embt_ind;

    @Column(name = "finl_lgl_evnt_ind")
    public String finl_lgl_evnt_ind;

    @Column(name = "hist_ind")
    public String hist_ind;

    @Column(name = "oprg_spec_evnt_ind")
    public String oprg_spec_evnt_ind;

    @Column(name = "othr_spec_evnt_ind")
    public String othr_spec_evnt_ind;

    @Column(name = "scrd_flng_ind")
    public String scrd_flng_ind;

    @Column(name = "suit_jdgt_ind")
    public String suit_jdgt_ind;

    @Column(name = "act_pay")
    public Double act_pay;

    @Column(name = "act_rec")
    public Double act_rec;

    @Column(name = "ann_sale_cons_ind")
    public String ann_sale_cons_ind;

    @Column(name = "ann_sale_crcy_cd")
    public String ann_sale_crcy_cd;

    @Column(name = "ann_sale_vol")
    public Double ann_sale_vol;

    @Column(name = "audt_ind")
    public String audt_ind;

    @Column(name = "audt_qlfn_ind")
    public String audt_qlfn_ind;

    @Column(name = "cash_liq_aset")
    public Double cash_liq_aset;

    @Column(name = "cons_ind")
    public String cons_ind;

    @Column(name = "curr_rato")
    public Double curr_rato;

    @Column(name = "defu_indn")
    public Integer defu_indn;

    @Column(name = "estd_ind")
    public String estd_ind;

    @Column(name = "fcst_ind")
    public String fcst_ind;

    @Column(name = "fisc_ind")
    public String fisc_ind;

    @Column(name = "fnal_ind")
    public String fnal_ind;

    @Column(name = "incm_stmt_dt")
    @Temporal(TemporalType.DATE)
    public Date incm_stmt_dt;

    @Column(name = "itng_aset")
    public Double itng_aset;

    @Column(name = "net_incm")
    public Double net_incm;

    @Column(name = "net_wrth")
    public Double net_wrth;

    @Column(name = "open_ind")
    public String open_ind;

    @Column(name = "prev_net_wrth")
    public Double prev_net_wrth;

    @Column(name = "prev_sls")
    public Double prev_sls;

    @Column(name = "prev_stmt_dt")
    @Temporal(TemporalType.DATE)
    public Date prev_stmt_dt;

    @Column(name = "pro_frma_ind")
    public String pro_frma_ind;

    @Column(name = "qk_rato")
    public Double qk_rato;

    @Column(name = "rest_ind")
    public String rest_ind;

    @Column(name = "sgnd_ind")
    public String sgnd_ind;

    @Column(name = "sls")
    public Double sls;

    @Column(name = "stk")
    public Double stk;

    @Column(name = "stmt_crcy_cd")
    public Integer stmt_crcy_cd;

    @Column(name = "stmt_dt")
    @Temporal(TemporalType.DATE)
    public Date stmt_dt;

    @Column(name = "stmt_from_dt")
    @Temporal(TemporalType.DATE)
    public Date stmt_from_dt;

    @Column(name = "stmt_to_dt")
    @Temporal(TemporalType.DATE)
    public Date stmt_to_dt;

    @Column(name = "tang_net_wrth")
    public Double tang_net_wrth;

    @Column(name = "tang_net_wrth_crcy_cd")
    public String tang_net_wrth_crcy_cd;

    @Column(name = "tang_net_wrth_estd_ind")
    public String tang_net_wrth_estd_ind;

    @Column(name = "tot_aset")
    public Double tot_aset;

    @Column(name = "tot_curr_aset")
    public Double tot_curr_aset;

    @Column(name = "tot_curr_liab")
    public Double tot_curr_liab;

    @Column(name = "tot_liab")
    public Double tot_liab;

    @Column(name = "trl_bal_ind")
    public String trl_bal_ind;

    @Column(name = "ubal_ind")
    public String ubal_ind;

    @Column(name = "ann_sale_estd_ind")
    public String ann_sale_estd_ind;

}
