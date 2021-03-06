package com.alex.ws.domain.portfolio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sahm on 28.08.17.
 */
@Component
public class ConvertPortfolio {

    private static final Logger logger = LoggerFactory.getLogger(ConvertPortfolio.class);
    private final int WORKFLOW_ID = 1;
    private final int EXTERNAL_ID = 1; // BHC=1

    public List<Portfolio> apply(IncomingPortfolio incomingPortfolio) {
        List<Portfolio> portfolios = new ArrayList<>();
        incomingPortfolio.companies.forEach(company -> portfolios.add(createPortfolio(incomingPortfolio, company)));
        logger.info("list.size: {}", portfolios.size());
        return portfolios;
    }

    private Portfolio createPortfolio(IncomingPortfolio incomingPortfolio, Company company) {
        Portfolio portfolio = new Portfolio();
        portfolio.pfl_wrk_id = WORKFLOW_ID;
        portfolio.pfl_strt_dt = new Date();
        portfolio.pfl_end_dt = null;
        portfolio.pfl_country_iso2 = company.country;
        portfolio.pfl_cust_identifier = company.id;
        portfolio.pfl_ext_identifier = Integer.valueOf(company.business_partner_id);
        portfolio.pfl_dtt_id = GlobalMapping.getProfileIdValue(company.data_profile.toUpperCase());
        if (portfolio.pfl_dtt_id == null) {
            portfolio.pfl_dtt_id = GlobalMapping.getProfileIdValue("SMALL");
        }
        portfolio.pfl_csg_id = GlobalMapping.getSystemIdValue(incomingPortfolio.system_id.toUpperCase());
        logger.info("createPortfolio():  {}", portfolio.toString());
        return portfolio;
    }
}
