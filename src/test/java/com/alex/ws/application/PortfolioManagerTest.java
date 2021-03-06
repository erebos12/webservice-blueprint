package com.alex.ws.application;

import com.alex.ws.infrastructure.PortfolioRepository;
import com.alex.bhc.domain.portfolio.Portfolio;
import com.alex.bhc.infrastructure.PortfolioRepository;
import com.alex.bhc.utils.H2DbInitializer;
import com.alex.bhc.utils.Sorter;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
@RunWith(SpringRunner.class)
@SpringBootTest
public class PortfolioManagerTest {

    @Autowired
    public PortfolioManager portfolioManager;
    @Autowired
    private PortfolioRepository portfolioRepository;


    @BeforeClass
    public static void setup() throws SQLException {
        H2DbInitializer.initializeH2();
    }

    @Before
    public void cleanup() {
        portfolioRepository.deleteAll();
    }

    @Test
    public void whenInsert_P1AndP2_thenExpectBoth_with_NoEndDate() throws Exception {
        //when
        Portfolio p1 = new Portfolio();
        p1.pfl_cust_identifier = "123";
        p1.pfl_country_iso2 = "DE";
        p1.pfl_strt_dt = new Date();
        p1.pfl_csg_id = 3;
        p1.pfl_dtt_id = 1;
        p1.pfl_ext_identifier = 444;
        p1.pfl_wrk_id = 1;

        Portfolio p2 = new Portfolio();
        p2.pfl_cust_identifier = "123";
        p2.pfl_country_iso2 = "DE";
        p2.pfl_strt_dt = new Date();
        p2.pfl_csg_id = 3;
        p2.pfl_dtt_id = 2;
        p2.pfl_ext_identifier = 444;
        p2.pfl_wrk_id = 1;

        //then
        portfolioManager.disableAllAndInsertNewPortfolio(Arrays.asList(p1, p2));

        //expect
        List<Portfolio> portfolioList = portfolioManager.getPortfolio("P4S");
        Sorter.sortListByPortfolioID(portfolioList);
        assertEquals(2, portfolioList.size());
        assertThat(portfolioList.get(0).pfl_end_dt, is(IsNull.nullValue()));
        assertThat(portfolioList.get(1).pfl_end_dt, is(IsNull.nullValue()));
    }

    @Test
    public void whenUpdateSamePortfolio_thenExpect_just_EndDateForOldPortfolio() throws Exception {
        List<Portfolio> portfolioList;
        //initial portfolio
        Portfolio p1 = new Portfolio();
        p1.pfl_cust_identifier = "123";
        p1.pfl_country_iso2 = "DE";
        p1.pfl_strt_dt = new Date();
        p1.pfl_csg_id = 1;
        p1.pfl_dtt_id = 1;
        p1.pfl_ext_identifier = 444;
        p1.pfl_wrk_id = 1;

        // untouched portfolio
        Portfolio p2 = new Portfolio();
        p2.pfl_cust_identifier = "9678";
        p2.pfl_country_iso2 = "DE";
        p2.pfl_strt_dt = new Date();
        p2.pfl_csg_id = 2;
        p2.pfl_dtt_id = 5;
        p2.pfl_ext_identifier = 555;
        p2.pfl_wrk_id = 1;
        portfolioManager.disableAllAndInsertNewPortfolio(Arrays.asList(p1, p2));

        portfolioList = portfolioManager.getPortfolio("PBC");
        assertEquals(1, portfolioList.size());
        assertThat(portfolioList.get(0).pfl_end_dt, is(IsNull.nullValue()));

        portfolioList = portfolioManager.getPortfolio("P2R");
        assertEquals(1, portfolioList.size());
        assertThat(portfolioList.get(0).pfl_end_dt, is(IsNull.nullValue()));

        // now updateEndDatesBy P1 with new pfl_dtt_id
        p1 = new Portfolio();
        p1.pfl_cust_identifier = "123";
        p1.pfl_country_iso2 = "DE";
        p1.pfl_strt_dt = new Date();
        p1.pfl_csg_id = 1;
        p1.pfl_dtt_id = 2; // updateEndDatesBy in here
        p1.pfl_ext_identifier = 444;
        p1.pfl_wrk_id = 1;
        portfolioManager.disableAllAndInsertNewPortfolio(Arrays.asList(p1));

        // check content of table
        portfolioList = portfolioManager.getPortfolio("P2R");
        assertEquals(2, portfolioList.size());
        Sorter.sortListByPortfolioID(portfolioList);
        assertThat(portfolioList.get(0).pfl_end_dt, is(IsNull.notNullValue()));
        assertThat(portfolioList.get(1).pfl_end_dt, is(IsNull.nullValue()));

        portfolioList = portfolioManager.getPortfolio("PBC");
        assertEquals(1, portfolioList.size());
        assertThat(portfolioList.get(0).pfl_end_dt, is(IsNull.nullValue()));
    }
}
