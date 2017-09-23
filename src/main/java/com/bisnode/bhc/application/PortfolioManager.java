package com.bisnode.bhc.application;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.GlobalMapping;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.PortfolioTableMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sahm on 23.08.17.
 */
@Component
public class PortfolioManager {

    @Autowired
    private PortfolioTableMgr portfolioTableMgr;

    @Autowired
    public PortfolioManager(CfgParams cfgParams, PortfolioTableMgr portfolioTableMgr) throws IOException {
        this.portfolioTableMgr = portfolioTableMgr;
    }

    public void update(List<Portfolio> portfolioList) {
        if (portfolioList.isEmpty()){
            return;
        }
        portfolioTableMgr.updateEndDatesBy(portfolioList.get(0).pfl_csg_id);
        portfolioList.forEach(portfolio -> portfolioTableMgr.insert(portfolio));
    }

    public List<Portfolio> getPortfolio(String system_id) {
        Integer mappedSystemId = getSystemIdValue(system_id);
        return portfolioTableMgr.selectPortfolioBy(mappedSystemId);
    }

    public List<Portfolio> getActivePortfolio(String system_id) {
        Integer mappedSystemId = getSystemIdValue(system_id);
        List<Portfolio> list = portfolioTableMgr.selectPortfolioBy(mappedSystemId);
        return list.stream().filter(portItem -> hasNoEndDate(portItem.pfl_end_dt)).collect(Collectors.toList());
    }

    private Integer getSystemIdValue(String system_id) {
        return GlobalMapping.systemIdMap.get(system_id.toUpperCase());
    }

    private boolean hasNoEndDate(Date endDate) {
        return endDate == null;
    }
}
