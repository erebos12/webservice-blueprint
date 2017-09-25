package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.portfolio.Portfolio;
import com.bisnode.bhc.utils.H2DbInitializer;
import com.bisnode.bhc.utils.PortfolioSampleCfg;
import org.hamcrest.core.IsNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PortfolioDbOperatorTest {

    @Autowired
    private PortfolioDbOperator portfolioDbOperator;

    @BeforeClass
    public static void setup() {
        System.setProperty("BHCWS_MODE", "test");
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
