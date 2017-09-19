package com.bisnode.bhc.application;

import com.bisnode.bhc.configuration.CfgParams;
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
        SelectColumnProperty pfl_csg_id_criteria = new SelectColumnProperty("pfl_csg_id", Arrays.asList(1));
        return tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(pfl_csg_id_criteria));
    }
}
