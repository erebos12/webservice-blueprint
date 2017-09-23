package com.bisnode.bhc.application;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.GlobalMapping;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.PortfolioDbOperator;
import com.bisnode.bhc.utils.H2DbInitializer;
import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sahm on 23.08.17.
 */
@Component
public class PortfolioManager {

    private PortfolioDbOperator portfolioDbOperator;
    private CfgParams cfgParams;

    @Autowired
    public PortfolioManager(PortfolioDbOperator portfolioDbOperator, CfgParams cfgParams) throws IOException, SQLException {
        this.portfolioDbOperator = portfolioDbOperator;
        this.cfgParams = cfgParams;
        if ("test".equalsIgnoreCase(cfgParams.mode)){
            String h2TestDataFile = "bhc-data-h2.sql";
            String h2CfgFile = Resources.getResource(h2TestDataFile).getFile();
            H2DbInitializer.initializeH2(h2CfgFile);
        }
    }

    public void update(List<Portfolio> portfolioList) {
        if (portfolioList.isEmpty()){
            return;
        }
        portfolioDbOperator.updateEndDatesBy(portfolioList.get(0).pfl_csg_id);
        portfolioList.forEach(portfolio -> portfolioDbOperator.insert(portfolio));
    }

    public List<Portfolio> getPortfolio(String system_id) {
        Integer mappedSystemId = getSystemIdValue(system_id);
        return portfolioDbOperator.selectPortfolioBy(mappedSystemId);
    }

    public List<Portfolio> getActivePortfolio(String system_id) {
        Integer mappedSystemId = getSystemIdValue(system_id);
        List<Portfolio> list = portfolioDbOperator.selectPortfolioBy(mappedSystemId);
        return list.stream().filter(portItem -> hasNoEndDate(portItem.pfl_end_dt)).collect(Collectors.toList());
    }

    private Integer getSystemIdValue(String system_id) {
        return GlobalMapping.systemIdMap.get(system_id.toUpperCase());
    }

    private boolean hasNoEndDate(Date endDate) {
        return endDate == null;
    }
}
