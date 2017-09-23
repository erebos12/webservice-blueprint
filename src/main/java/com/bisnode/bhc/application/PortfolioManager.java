package com.bisnode.bhc.application;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.GlobalMapping;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.DbTableMgr;
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

    private DbTableMgr dbTableMgr;
    private CfgParams cfgParams;

    @Autowired
    public PortfolioManager(DbTableMgr dbTableMgr, CfgParams cfgParams) throws IOException, SQLException {
        this.dbTableMgr = dbTableMgr;
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
        dbTableMgr.updateEndDatesBy(portfolioList.get(0).pfl_csg_id);
        portfolioList.forEach(portfolio -> dbTableMgr.insert(portfolio));
    }

    public List<Portfolio> getPortfolio(String system_id) {
        Integer mappedSystemId = getSystemIdValue(system_id);
        return dbTableMgr.selectPortfolioBy(mappedSystemId);
    }

    public List<Portfolio> getActivePortfolio(String system_id) {
        Integer mappedSystemId = getSystemIdValue(system_id);
        List<Portfolio> list = dbTableMgr.selectPortfolioBy(mappedSystemId);
        return list.stream().filter(portItem -> hasNoEndDate(portItem.pfl_end_dt)).collect(Collectors.toList());
    }

    private Integer getSystemIdValue(String system_id) {
        return GlobalMapping.systemIdMap.get(system_id.toUpperCase());
    }

    private boolean hasNoEndDate(Date endDate) {
        return endDate == null;
    }
}
