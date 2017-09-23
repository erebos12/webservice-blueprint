package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.utils.TestH2Initializer;
import com.bisnode.bhc.utils.PortfolioSampleCfg;
import org.hamcrest.core.IsNull;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class PortfolioTableMgrTest {

    private static PortfolioTableMgr portfolioTableMgr = null;
    private static CfgParams cfgParams = new CfgParams();

    @BeforeClass
    public static void setup() throws SQLException, RuntimeException, IOException {
        portfolioTableMgr = new PortfolioTableMgr();
    }

    @Test
    public void whenInsertToDifferentPortfolios_thenExpect_them_in_DB() throws Exception {
        TestH2Initializer.initializeH2(cfgParams.getH2DataFile());
        portfolioTableMgr.insert(PortfolioSampleCfg.getPortfolioCompany2());
        portfolioTableMgr.insert(PortfolioSampleCfg.getPortfolioCompany3());

        List<Portfolio> portfolioList = portfolioTableMgr.selectPortfolioBy(1);
        assertEquals(2, portfolioList.size());
    }

    @Test
    public void testSetEnddates() throws IOException, SQLException {
        TestH2Initializer.initializeH2(cfgParams.getH2DataFile());
        portfolioTableMgr.insert(PortfolioSampleCfg.getPortfolioCompany1());
        portfolioTableMgr.insert(PortfolioSampleCfg.getPortfolioCompany3());
        portfolioTableMgr.updateEndDatesBy(1);
        List<Portfolio> l = portfolioTableMgr.selectPortfolioBy(1);
        assertThat(l.size(), is(1));
        Date endDate = l.get(0).pfl_end_dt;
        assertThat(endDate, is(IsNull.notNullValue()));
    }
}
