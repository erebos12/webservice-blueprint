package com.bisnode.bhc.application;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.TableUpserter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by sahm on 23.08.17.
 */
@Component
public class PortfolioManager {

    private TableUpserter tableUpserter;

    public PortfolioManager() throws IOException {
        this.tableUpserter = new TableUpserter(CfgParams.getHibernateCfgFile(), CfgParams.getHibernateTables());
    }

    public void update(List<Portfolio> portfolioList) {
        tableUpserter.updateAllEndDates();
        portfolioList.forEach(portfolio -> tableUpserter.upsert(portfolio));
    }
}
