package com.bisnode.bhc.application;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.GlobalMapping;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.SelectColumnProperty;
import com.bisnode.bhc.infrastructure.TableSelector;
import com.bisnode.bhc.infrastructure.TableUpserter;
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

    private TableUpserter tableUpserter;
    private TableSelector tableSelector;
    private static final String SYSTEM_ID_COLUMN = "pfl_csg_id";

    @Autowired
    public PortfolioManager(CfgParams cfgParams) throws IOException {
        this.tableUpserter = new TableUpserter(cfgParams.getHibernateCfgFile(), cfgParams.getHibernateTables());
        this.tableSelector = new TableSelector(cfgParams.getHibernateCfgFile(), cfgParams.getHibernateTables());
    }

    public void update(List<Portfolio> portfolioList) {
        tableUpserter.updateAllEndDates();
        portfolioList.forEach(portfolio -> tableUpserter.upsert(portfolio));
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
