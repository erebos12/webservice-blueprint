package com.bisnode.bhc.application;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.GlobalMapping;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.SelectColumnProperty;
import com.bisnode.bhc.infrastructure.TableSelector;
import com.bisnode.bhc.infrastructure.PortfolioTableUpserter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sahm on 23.08.17.
 */
@Component
public class PortfolioManager {

    @Autowired
    private PortfolioTableUpserter portfolioTableUpserter;
    private TableSelector tableSelector;
    private static final String SYSTEM_ID_COLUMN = "pfl_csg_id";

    @Autowired
    public PortfolioManager(CfgParams cfgParams, PortfolioTableUpserter portfolioTableUpserter) throws IOException {
        this.tableSelector = new TableSelector(cfgParams.getHibernateCfgFile(), cfgParams.getHibernateTables());
        this.portfolioTableUpserter = portfolioTableUpserter;
    }

    public void update(List<Portfolio> portfolioList) {
        if (portfolioList.isEmpty()){
            return;
        }
        portfolioTableUpserter.update(portfolioList.get(0).pfl_csg_id);
        portfolioList.forEach(portfolio -> portfolioTableUpserter.insert(portfolio));
    }

    public List<Portfolio> getPortfolio(String system_id) {
        Integer mappedSystemId = getSystemIdValue(system_id);
        SelectColumnProperty pfl_csg_id_criteria = new SelectColumnProperty(SYSTEM_ID_COLUMN, Arrays.asList(mappedSystemId));
        return tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(pfl_csg_id_criteria));
    }

    public List<Portfolio> getActivePortfolio(String system_id) {
        Integer mappedSystemId = getSystemIdValue(system_id);
        SelectColumnProperty pfl_csg_id_criteria = new SelectColumnProperty(SYSTEM_ID_COLUMN, Arrays.asList(mappedSystemId));
        List<Portfolio> list = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(pfl_csg_id_criteria));
        return list.stream().filter(portItem -> hasNoEndDate(portItem.pfl_end_dt)).collect(Collectors.toList());
    }

    private Integer getSystemIdValue(String system_id) {
        return GlobalMapping.systemIdMap.get(system_id.toUpperCase());
    }

    private boolean hasNoEndDate(Date endDate) {
        return endDate == null;
    }
}
