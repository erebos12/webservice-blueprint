package com.bisnode.bhc.application;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.SelectColumnProperty;
import com.bisnode.bhc.infrastructure.TableSelector;
import com.bisnode.bhc.infrastructure.h2.TestH2Initializer;
import org.hamcrest.core.IsNull;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by sahm on 23.08.17.
 */
public class PortfolioManagerTest {

    private static PortfolioManager portfolioManager;
    private static TableSelector tableSelector = null;

    @BeforeClass
    public static void setup() throws SQLException, RuntimeException, IOException {
        TestH2Initializer.initializeH2(CfgParams.getH2DataFile());
        portfolioManager = new PortfolioManager(CfgParams.getHibernateCfgFile());
        tableSelector = new TableSelector(CfgParams.getHibernateCfgFile(), Arrays.asList(Portfolio.class));
    }

    @Test
    public void test_updatePortfolio() throws Exception {
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
        p2.PFL_CLT_ID = 100;
        p2.PFL_CDP_ID = 12;
        p2.PFL_COUNTRY_ISO3 = "DE";
        p2.PFL_CUST_ID = "111";
        p2.PFL_CURRENCY_ISO3 = "EUR";
        p2.PFL_DTT_ID = 3;
        p2.PFL_LEDGER_KEY = "SQUID";
        p2.PFL_WRK_ID = 2;
        p2.PFL_END_DATE = null;
        p2.PFL_START_DATE = new Date();

        portfolioManager.update(Arrays.asList(p2, p));

        SelectColumnProperty critDepartment = new SelectColumnProperty("PFL_CLT_ID", Arrays.asList(99, 100));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
        assertEquals(2, portfolioList.size());

        p.PFL_CLT_ID = 99;
        p.PFL_CDP_ID = 11;
        p.PFL_COUNTRY_ISO3 = "DE";
        p.PFL_CUST_ID = "9999";
        p.PFL_CURRENCY_ISO3 = "EUR";
        p.PFL_DTT_ID = 8;
        p.PFL_LEDGER_KEY = "SQUID";
        p.PFL_WRK_ID = 1;
        p.PFL_END_DATE = null;
        p.PFL_START_DATE = new Date();

        p2.PFL_CLT_ID = 1;
        p2.PFL_CDP_ID = 11;
        p2.PFL_COUNTRY_ISO3 = "DE";
        p2.PFL_CUST_ID = "9999";
        p2.PFL_CURRENCY_ISO3 = "EUR";
        p2.PFL_DTT_ID = 8;
        p2.PFL_LEDGER_KEY = "SQUID";
        p2.PFL_WRK_ID = 1;
        p2.PFL_END_DATE = null;
        p2.PFL_START_DATE = new Date();

        portfolioManager.update(Arrays.asList(p2, p));

        critDepartment = new SelectColumnProperty("PFL_CLT_ID", Arrays.asList(100));
        portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
        assertEquals(1, portfolioList.size());
        assertThat(portfolioList.get(0).PFL_END_DATE, is(IsNull.notNullValue()));

        critDepartment = new SelectColumnProperty("PFL_CLT_ID", Arrays.asList(99));
        portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
        assertThat(portfolioList.get(0).PFL_CURRENCY_ISO3, is("EUR"));
        assertThat(portfolioList.get(0).PFL_END_DATE, is(IsNull.nullValue()));

        critDepartment = new SelectColumnProperty("PFL_CLT_ID", Arrays.asList(1));
        portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
        assertThat(portfolioList.get(0).PFL_END_DATE, is(IsNull.nullValue()));

    }

    @Test(expected = org.hibernate.exception.ConstraintViolationException.class)
    public void whenUpdateWithInvalidPortfolio_thenExpect_Exception() throws Exception {
        Portfolio p = new Portfolio();
        portfolioManager.update(Arrays.asList(p));
    }
}
