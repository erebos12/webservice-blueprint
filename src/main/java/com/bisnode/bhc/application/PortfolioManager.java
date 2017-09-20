package com.bisnode.bhc.application;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.GlobalMapping;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.SelectColumnProperty;
import com.bisnode.bhc.infrastructure.TableSelector;
import com.bisnode.bhc.infrastructure.TableUpserter;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sahm on 23.08.17.
 */
@Component
public class PortfolioManager {

    private TableUpserter tableUpserter;
    private TableSelector tableSelector;

    public PortfolioManager() throws IOException {
        this.tableUpserter = new TableUpserter(CfgParams.getHibernateCfgFile(), CfgParams.getHibernateTables());
        this.tableSelector = new TableSelector(CfgParams.getHibernateCfgFile(), CfgParams.getHibernateTables());
    }

    public void update(List<Portfolio> portfolioList) {
        tableUpserter.updateAllEndDates();
        portfolioList.forEach(portfolio -> tableUpserter.upsert(portfolio));
    }

    public List<Portfolio> getPortfolio(String system_id) {
        Integer mappedSystemId = GlobalMapping.systemIdMap.get(system_id.toUpperCase());
        SelectColumnProperty pfl_csg_id_criteria = new SelectColumnProperty("pfl_csg_id", Arrays.asList(mappedSystemId));
        return tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(pfl_csg_id_criteria));
    }

    public List<Portfolio> getActivePortfolio(String system_id) {
        Integer mappedSystemId = GlobalMapping.systemIdMap.get(system_id.toUpperCase());
        SelectColumnProperty pfl_csg_id_criteria = new SelectColumnProperty("pfl_csg_id", Arrays.asList(mappedSystemId));
        List<Portfolio> list = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(pfl_csg_id_criteria));
        return list.stream().filter(i -> i.pfl_end_dt == null).collect(Collectors.toList());
    }
}
