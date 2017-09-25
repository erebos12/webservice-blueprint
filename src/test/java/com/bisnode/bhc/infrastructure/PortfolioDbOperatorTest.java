package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.utils.PortfolioSampleCfg;
import com.bisnode.bhc.utils.H2DbInitializer;
import com.google.common.io.Resources;
import org.hamcrest.core.IsNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class PortfolioDbOperatorTest {

    private static PortfolioDbOperator portfolioDbOperator = null;

    @BeforeClass
    public static void setup() throws SQLException, RuntimeException, IOException {
        portfolioDbOperator = new PortfolioDbOperator(new CfgParams());
    }

    @Test
    public void whenInsertToDifferentPortfolios_thenExpect_them_in_DB() throws Exception {
        H2DbInitializer.initializeH2();
        portfolioDbOperator.insert(PortfolioSampleCfg.getPortfolioCompany2());
        portfolioDbOperator.insert(PortfolioSampleCfg.getPortfolioCompany3());

        List<Portfolio> portfolioList = portfolioDbOperator.selectPortfolioBy(1);
        assertEquals(2, portfolioList.size());
    }

    @Test
    public void testSetEnddates() throws IOException, SQLException {
        H2DbInitializer.initializeH2();
        portfolioDbOperator.insert(PortfolioSampleCfg.getPortfolioCompany1());
        portfolioDbOperator.insert(PortfolioSampleCfg.getPortfolioCompany3());
        portfolioDbOperator.updateEndDatesBy(1);
        List<Portfolio> l = portfolioDbOperator.selectPortfolioBy(1);
        assertThat(l.size(), is(1));
        Date endDate = l.get(0).pfl_end_dt;
        assertThat(endDate, is(IsNull.notNullValue()));
    }
}
