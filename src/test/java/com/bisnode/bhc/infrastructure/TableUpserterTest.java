package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.utils.TestH2Initializer;
import com.bisnode.bhc.utils.PortfolioSampleCfg;
import org.hamcrest.core.IsNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class TableUpserterTest {

    private static TableUpserter tableUpserter = null;
    private static TableSelector tableSelector = null;

    @BeforeClass
    public static void setup() throws SQLException, RuntimeException, IOException {
        tableUpserter = new TableUpserter(CfgParams.getHibernateCfgFile(), Arrays.asList(Portfolio.class));
        tableSelector = new TableSelector(CfgParams.getHibernateCfgFile(), Arrays.asList(Portfolio.class));
    }

    @Test
    public void whenInsertToDifferentPortfolios_thenExpect_them_in_DB() throws Exception {
        TestH2Initializer.initializeH2(CfgParams.getH2DataFile());
        tableUpserter.upsert(PortfolioSampleCfg.getPortfolioCompany3());
        tableUpserter.upsert(PortfolioSampleCfg.getPortfolioCompany4());

        SelectColumnProperty critDepartment = new SelectColumnProperty("pfl_cust_identifier", Arrays.asList("77777", "88888"));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
        assertEquals(2, portfolioList.size());
    }

    @Test
    public void whenUpdateAnExistingPortfolio_thenExpect_updated_pfl_wrk_id() throws Exception {
        TestH2Initializer.initializeH2(CfgParams.getH2DataFile());
        Portfolio p1 = PortfolioSampleCfg.getPortfolioCompany1();
        tableUpserter.upsert(p1);
        SelectColumnProperty critDepartment = new SelectColumnProperty("pfl_wrk_id", Arrays.asList(33));
        List<Portfolio> portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(critDepartment));
        assertThat(portfolioList.size(), is(1));
        assertThat(portfolioList.get(0).pfl_country_iso2, is(p1.pfl_country_iso2));
        assertThat(portfolioList.get(0).pfl_cust_identifier, is(p1.pfl_cust_identifier));
        assertThat(portfolioList.get(0).pfl_dtt_id, is(p1.pfl_dtt_id));
        assertThat(portfolioList.get(0).pfl_csg_id, is(p1.pfl_csg_id));
        assertThat(portfolioList.get(0).pfl_wrk_id, is(p1.pfl_wrk_id));
        assertThat(portfolioList.get(0).pfl_ext_identifier, is(p1.pfl_ext_identifier));
        assertThat(portfolioList.get(0).pfl_end_dt, is(IsNull.nullValue()));
        assertThat(portfolioList.get(0).pfl_strt_dt, is(IsNull.notNullValue()));

        Portfolio p1_updated = PortfolioSampleCfg.getPortfolioCompany1Updated();
        tableUpserter.upsert(p1_updated);

        SelectColumnProperty select = new SelectColumnProperty("pfl_wrk_id", Arrays.asList(44));
        portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(select));
        assertThat(portfolioList.size(), is(1));
        assertThat(portfolioList.get(0).pfl_end_dt, is(IsNull.nullValue()));
        assertThat(portfolioList.get(0).pfl_strt_dt, is(IsNull.notNullValue()));

        int updatedEntries = tableUpserter.updateAllEndDates();
        assertThat(updatedEntries, is(2));
        portfolioList = tableSelector.selectWhereInMultipleList(Portfolio.class, Arrays.asList(select));
        assertThat(portfolioList.get(0).pfl_strt_dt, is(IsNull.notNullValue()));
    }
}
