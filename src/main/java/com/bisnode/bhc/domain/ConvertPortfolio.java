package com.bisnode.bhc.domain;

import com.bisnode.bhc.domain.exception.InvalidDataProfileException;
import com.bisnode.bhc.domain.exception.InvalidSystemIdException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sahm on 28.08.17.
 */
@Component
public class ConvertPortfolio {

    private HashMap<String, Integer> systemIdMap = new HashMap<>();
    private HashMap<String, Integer> profileIdMap = new HashMap<>();
    private final int WORKFLOW_ID = 1;
    private final int EXTERNAL_ID = 1; // BHC=1
    private List<Portfolio> portfolios = new ArrayList<>();

    public ConvertPortfolio() {
        this.systemIdMap.put("PBC", 1);
        this.systemIdMap.put("P2R", 2);
        this.systemIdMap.put("P4S", 3);

        this.profileIdMap.put("Small", 1);
        this.profileIdMap.put("Medium", 2);
        this.profileIdMap.put("Large", 3);
    }

    public List<Portfolio> apply(IncomingPortfolio incomingPortfolio) throws InvalidDataProfileException, InvalidSystemIdException {
        for (Company company : incomingPortfolio.companies) {
            portfolios.add(createPortfolio(incomingPortfolio, company));
        }
        return portfolios;
    }

    private Portfolio createPortfolio(IncomingPortfolio incomingPortfolio, Company company) throws InvalidDataProfileException, InvalidSystemIdException {
        Portfolio portfolio = new Portfolio();
        portfolio.pfl_wrk_id = WORKFLOW_ID;
        portfolio.pfl_ext_identifier = EXTERNAL_ID;
        portfolio.pfl_strt_dt = new Date();
        portfolio.pfl_end_dt = null;
        portfolio.pfl_country_iso2 = company.country;
        portfolio.pfl_cust_identifier = company.id;
        if (profileIdMap.get(company.data_profile) == null) {
            throw new InvalidDataProfileException(company.id, company.data_profile);
        }
        portfolio.pfl_dtt_id = profileIdMap.get(company.data_profile);
        if (systemIdMap.get(incomingPortfolio.system_id) == null) {
            throw new InvalidSystemIdException(company.id, incomingPortfolio.system_id);
        }
        portfolio.pfl_csg_id = systemIdMap.get(incomingPortfolio.system_id);
        return portfolio;
    }
}
