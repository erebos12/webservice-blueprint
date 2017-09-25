package com.bisnode.bhc.utils;

import com.bisnode.bhc.domain.portfolio.Portfolio;

import java.util.Date;

/**
 * Created by sahm on 24.08.17.
 */
public class PortfolioSampleCfg {


    private static Portfolio portfolioCompany1;
    private static Portfolio portfolioCompany3;
    private static Portfolio portfolioCompany4;

    public static Portfolio getPortfolioCompany1() {
        portfolioCompany1 = new Portfolio();
        portfolioCompany1.pfl_country_iso2 = "DE";
        portfolioCompany1.pfl_csg_id = 3;
        portfolioCompany1.pfl_wrk_id = 33;
        portfolioCompany1.pfl_ext_identifier = 333;
        portfolioCompany1.pfl_dtt_id = 3333;
        portfolioCompany1.pfl_cust_identifier = "33333";
        portfolioCompany1.pfl_strt_dt = new Date();
        System.out.println("Portfolio 1: " + portfolioCompany1.toString());
        return portfolioCompany1;
    }

    public static Portfolio getPortfolioCompany2() {
        portfolioCompany3 = new Portfolio();
        portfolioCompany3.pfl_country_iso2 = "SE";
        portfolioCompany3.pfl_csg_id = 1;
        portfolioCompany3.pfl_wrk_id = 77;
        portfolioCompany3.pfl_ext_identifier = 777;
        portfolioCompany3.pfl_dtt_id = 7777;
        portfolioCompany3.pfl_cust_identifier = "77777";
        portfolioCompany3.pfl_strt_dt = new Date();
        System.out.println("Portfolio 3: " + portfolioCompany3.toString());
        return portfolioCompany3;
    }

    public static Portfolio getPortfolioCompany3() {
        portfolioCompany4 = new Portfolio();
        portfolioCompany4.pfl_country_iso2 = "SE";
        portfolioCompany4.pfl_csg_id = 1;
        portfolioCompany4.pfl_wrk_id = 77;
        portfolioCompany4.pfl_ext_identifier = 888;
        portfolioCompany4.pfl_dtt_id = 8888;
        portfolioCompany4.pfl_cust_identifier = "88888";
        portfolioCompany4.pfl_strt_dt = new Date();
        System.out.println("Portfolio 4: " + portfolioCompany4.toString());
        return portfolioCompany4;
    }
}
