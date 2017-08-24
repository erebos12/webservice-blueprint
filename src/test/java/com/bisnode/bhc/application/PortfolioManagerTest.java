package com.bisnode.bhc.application;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.infrastructure.PortfolioSampleCfg;
import com.bisnode.bhc.infrastructure.SelectColumnProperty;
import com.bisnode.bhc.infrastructure.TableSelector;
import com.bisnode.bhc.infrastructure.h2.TestH2Initializer;
import com.bisnode.bhc.utils.Sorter;
import org.hamcrest.core.IsNull;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
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
    public void whenUpdateP1_and_AddP3_thenExpect_EndDateForP2() throws Exception {
        //initial portfolio
        Portfolio p1 = PortfolioSampleCfg.getPortfolioCompany1();
        Portfolio p2 = PortfolioSampleCfg.getPortfolioCompany2();
        portfolioManager.update(Arrays.asList(p1, p2));

        // just add p3 and update p1 again
        Portfolio p3 = PortfolioSampleCfg.getPortfolioCompany3();
        portfolioManager.update(Arrays.asList(p1, p3));

        //then expect only p2 must have end date (portfolio is disabled)
        SelectColumnProperty selectCrit = new SelectColumnProperty("pfl_id", Arrays.asList(p1.pfl_id, p2.pfl_id, p3.pfl_id));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(selectCrit));
        assertEquals(3, portfolioList.size());
        Sorter.sortListByPortfolioID(portfolioList);
        assertThat(portfolioList.get(0).pfl_end_dt, is(IsNull.nullValue()));
        assertThat(portfolioList.get(1).pfl_end_dt, is(IsNull.notNullValue()));
        assertThat(portfolioList.get(2).pfl_end_dt, is(IsNull.nullValue()));
    }

    @Test(expected = org.hibernate.exception.ConstraintViolationException.class)
    public void whenUpdateWithInvalidPortfolio_thenExpect_Exception() throws Exception {
        Portfolio p = new Portfolio();
        portfolioManager.update(Arrays.asList(p));
    }
}
