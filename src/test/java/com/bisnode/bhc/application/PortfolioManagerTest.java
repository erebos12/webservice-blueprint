package com.bisnode.bhc.application;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.PortfolioTableUpserter;
import com.bisnode.bhc.utils.PortfolioSampleCfg;
import com.bisnode.bhc.infrastructure.SelectColumnProperty;
import com.bisnode.bhc.infrastructure.TableSelector;
import com.bisnode.bhc.utils.TestH2Initializer;
import com.bisnode.bhc.utils.Sorter;
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
        CfgParams cfgParams = new CfgParams();
        TestH2Initializer.initializeH2(cfgParams.getH2DataFile());
        portfolioManager = new PortfolioManager(cfgParams, new PortfolioTableUpserter());
        tableSelector = new TableSelector(cfgParams.getHibernateCfgFile(), Arrays.asList(Portfolio.class));
    }

    @Test
    public void whenInsert_P1AndP2_thenExpectBoth_with_NoEndDate() throws Exception {
        //when
        Portfolio p1 = PortfolioSampleCfg.getPortfolioCompany1();
        Portfolio p2 = PortfolioSampleCfg.getPortfolioCompany2();

        //then
        portfolioManager.update(Arrays.asList(p1, p2));

        //expect
        SelectColumnProperty critDepartment = new SelectColumnProperty("pfl_id", Arrays.asList(p1.pfl_id, p2.pfl_id));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
        Sorter.sortListByPortfolioID(portfolioList);
        assertEquals(2, portfolioList.size());
        assertThat(portfolioList.get(0).pfl_end_dt, is(IsNull.nullValue()));
        assertThat(portfolioList.get(0).pfl_end_dt, is(IsNull.nullValue()));
    }

    @Test
    public void whenUpdateSamePortfolio_thenExpect_just_EndDateForOldPortfolio() throws Exception {
        //initial portfolio
        Portfolio p1 = new Portfolio();
        p1.pfl_cust_identifier = "123";
        p1.pfl_country_iso2 = "DE";
        p1.pfl_strt_dt = new Date();
        p1.pfl_csg_id = 1;
        p1.pfl_dtt_id = 1;
        p1.pfl_ext_identifier = 444;
        p1.pfl_wrk_id = 1;
        portfolioManager.update(Arrays.asList(p1));

        // untouched portfolio
        Portfolio p2 = new Portfolio();
        p2.pfl_cust_identifier = "9678";
        p2.pfl_country_iso2 = "DE";
        p2.pfl_strt_dt = new Date();
        p2.pfl_csg_id = 2;
        p2.pfl_dtt_id = 5;
        p2.pfl_ext_identifier = 555;
        p2.pfl_wrk_id = 1;
        portfolioManager.update(Arrays.asList(p2));

        SelectColumnProperty selectCrit = new SelectColumnProperty("pfl_cust_identifier", Arrays.asList("123"));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(selectCrit));
        assertEquals(1, portfolioList.size());

        // now update P1 with new pfl_dtt_id
        p1 = new Portfolio();
        p1.pfl_cust_identifier = "123";
        p1.pfl_country_iso2 = "DE";
        p1.pfl_strt_dt = new Date();
        p1.pfl_csg_id = 1;
        p1.pfl_dtt_id = 2; // update in here
        p1.pfl_ext_identifier = 444;
        p1.pfl_wrk_id = 1;
        portfolioManager.update(Arrays.asList(p1));

        // check content of table
        selectCrit = new SelectColumnProperty("pfl_dtt_id", Arrays.asList(1));
        portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(selectCrit));
        assertEquals(1, portfolioList.size());
        Portfolio p1_old_with_end_date = portfolioList.get(0);

        selectCrit = new SelectColumnProperty("pfl_ext_identifier", Arrays.asList(555));
        portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(selectCrit));
        assertEquals(1, portfolioList.size());
        Portfolio portfolio_untouched_end_date = portfolioList.get(0);

        selectCrit = new SelectColumnProperty("pfl_dtt_id", Arrays.asList(2));
        portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(selectCrit));
        assertEquals(1, portfolioList.size());
        Portfolio p1_new_without_end_date = portfolioList.get(0);

        assertThat(p1_old_with_end_date.pfl_end_dt, is(IsNull.notNullValue()));
        assertThat(p1_new_without_end_date.pfl_end_dt, is(IsNull.nullValue()));
        assertThat(portfolio_untouched_end_date.pfl_end_dt, is(IsNull.nullValue()));
    }
}
