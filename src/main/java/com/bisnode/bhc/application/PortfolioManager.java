package com.bisnode.bhc.application;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.TableUpserter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by sahm on 23.08.17.
 */
public class PortfolioManager {

    private TableUpserter tableUpserter;

    @Autowired
    public PortfolioManager(URL hibernetCfgFile) throws IOException {
        this.tableUpserter = new TableUpserter(hibernetCfgFile, CfgParams.getHibernateTables());
    }

    public void update(List<Portfolio> portfolioList) {
        tableUpserter.updateAllEndDates();
        portfolioList.forEach(portfolio -> tableUpserter.upsert(portfolio));
    }
}
