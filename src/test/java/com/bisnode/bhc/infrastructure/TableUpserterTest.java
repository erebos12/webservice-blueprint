package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.h2.CfgParams;
import com.bisnode.bhc.infrastructure.h2.TestH2Initializer;
import org.hamcrest.core.IsNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class TableUpserterTest {

    private static TableUpserter tblInserter = null;
    private static TableSelector tableSelector = null;
    private static final Logger logger = LoggerFactory.getLogger(TableUpserterTest.class);
    private static final String PORTFOLIO_TABLE_UKEY_COLUMN = "PFL_CLT_ID";

    @BeforeClass
    public static void setup() throws SQLException, RuntimeException, IOException {
        TestH2Initializer.initializeH2(CfgParams.getH2DataFile());
        tblInserter = new TableUpserter(CfgParams.getHibernateCfgFile());
        tableSelector = new TableSelector(CfgParams.getHibernateCfgFile());
    }

    @Test
    public void whenInsertToDifferentPortfolios_thenExpect_them_in_DB() throws Exception {
        Portfolio p = new Portfolio();
        p.PFL_CLT_ID = 99;
        p.PFL_CDP_ID = 11;
        p.PFL_COUNTRY_ISO3 = "US";
        p.PFL_CUST_ID = "9999";
        p.PFL_CURRENCY_ISO3 = "USD";
        p.PFL_DTT_ID = 8;
        p.PFL_LEDGER_KEY = "SQUID";
        p.PFL_WRK_ID = 1;
        p.PFL_END_DATE = null;
        p.PFL_START_DATE = new Date();

        Portfolio p2 = new Portfolio();
        p2.PFL_CLT_ID = 9;
        p2.PFL_CDP_ID = 1;
        p2.PFL_COUNTRY_ISO3 = "RUS";
        p2.PFL_CUST_ID = "555";
        p2.PFL_CURRENCY_ISO3 = "RUS";
        p2.PFL_DTT_ID = 9;
        p2.PFL_LEDGER_KEY = "SQUID";
        p2.PFL_WRK_ID = 1;
        p2.PFL_END_DATE = null;
        p2.PFL_START_DATE = new Date();

        tblInserter.upsert(p);
        tblInserter.upsert(p2);

        SelectColumnProperty critDepartment = new SelectColumnProperty(PORTFOLIO_TABLE_UKEY_COLUMN, Arrays.asList(9, 99));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
        assertEquals(2, portfolioList.size());
    }

    @Test
    public void whenUpdateAnExistingPortfolio_thenExpect_updatedPortfolio() throws Exception {
        Portfolio originPortfolio = new Portfolio();
        originPortfolio.PFL_CLT_ID = 99;
        originPortfolio.PFL_CDP_ID = 11;
        originPortfolio.PFL_COUNTRY_ISO3 = "US";
        originPortfolio.PFL_CUST_ID = "888";
        originPortfolio.PFL_CURRENCY_ISO3 = "USD";
        originPortfolio.PFL_DTT_ID = 8;
        originPortfolio.PFL_LEDGER_KEY = "SQUID";
        originPortfolio.PFL_WRK_ID = 1;
        originPortfolio.PFL_END_DATE = null;
        originPortfolio.PFL_START_DATE = new Date();

        tblInserter.upsert(originPortfolio);
        SelectColumnProperty critDepartment = new SelectColumnProperty(PORTFOLIO_TABLE_UKEY_COLUMN, Arrays.asList(originPortfolio.PFL_CLT_ID));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
        assertThat(portfolioList.size(), is(1));
        assertThat(portfolioList.get(0).PFL_CUST_ID, is(originPortfolio.PFL_CUST_ID));
        assertThat(portfolioList.get(0).PFL_CDP_ID, is(originPortfolio.PFL_CDP_ID));
        assertThat(portfolioList.get(0).PFL_COUNTRY_ISO3, is(originPortfolio.PFL_COUNTRY_ISO3));
        assertThat(portfolioList.get(0).PFL_CURRENCY_ISO3, is(originPortfolio.PFL_CURRENCY_ISO3));
        assertThat(portfolioList.get(0).PFL_DTT_ID, is(originPortfolio.PFL_DTT_ID));
        assertThat(portfolioList.get(0).PFL_LEDGER_KEY, is(originPortfolio.PFL_LEDGER_KEY));
        assertThat(portfolioList.get(0).PFL_WRK_ID, is(originPortfolio.PFL_WRK_ID));
        assertThat(portfolioList.get(0).PFL_END_DATE, is(IsNull.nullValue()));
        assertThat(portfolioList.get(0).PFL_START_DATE, is(IsNull.notNullValue()));

        Portfolio updatedPortfolio = new Portfolio();
        updatedPortfolio.PFL_CLT_ID = 99;
        updatedPortfolio.PFL_CDP_ID = 12;
        updatedPortfolio.PFL_COUNTRY_ISO3 = "DE";
        updatedPortfolio.PFL_CUST_ID = "56476";
        updatedPortfolio.PFL_CURRENCY_ISO3 = "EUR";
        updatedPortfolio.PFL_DTT_ID = 9;
        updatedPortfolio.PFL_LEDGER_KEY = "SQUID";
        updatedPortfolio.PFL_WRK_ID = 2;
        updatedPortfolio.PFL_END_DATE = null;
        updatedPortfolio.PFL_START_DATE = new Date();
        tblInserter.upsert(updatedPortfolio);

        SelectColumnProperty select = new SelectColumnProperty(PORTFOLIO_TABLE_UKEY_COLUMN, Arrays.asList(updatedPortfolio.PFL_CLT_ID));
        portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(select));
        assertThat(portfolioList.get(0).PFL_CUST_ID, is(updatedPortfolio.PFL_CUST_ID));
        assertThat(portfolioList.get(0).PFL_CDP_ID, is(updatedPortfolio.PFL_CDP_ID));
        assertThat(portfolioList.get(0).PFL_COUNTRY_ISO3, is(updatedPortfolio.PFL_COUNTRY_ISO3));
        assertThat(portfolioList.get(0).PFL_CURRENCY_ISO3, is(updatedPortfolio.PFL_CURRENCY_ISO3));
        assertThat(portfolioList.get(0).PFL_DTT_ID, is(updatedPortfolio.PFL_DTT_ID));
        assertThat(portfolioList.get(0).PFL_LEDGER_KEY, is(updatedPortfolio.PFL_LEDGER_KEY));
        assertThat(portfolioList.get(0).PFL_WRK_ID, is(updatedPortfolio.PFL_WRK_ID));
        assertThat(portfolioList.get(0).PFL_END_DATE, is(IsNull.nullValue()));
        assertThat(portfolioList.get(0).PFL_START_DATE, is(IsNull.notNullValue()));
    }
}
