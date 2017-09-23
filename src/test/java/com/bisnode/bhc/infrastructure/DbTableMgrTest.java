package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.utils.PortfolioSampleCfg;
import com.bisnode.bhc.utils.TestH2Initializer;
import com.google.common.io.Resources;
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


public class DbTableMgrTest {

    private static DbTableMgr dbTableMgr = null;
    private static final String h2TestDataFile = "bhc-data-h2.sql";
    private static final String h2CfgFile = Resources.getResource(h2TestDataFile).getFile();

    @BeforeClass
    public static void setup() throws SQLException, RuntimeException, IOException {
        dbTableMgr = new DbTableMgr();
    }

    @Test
    public void whenInsertToDifferentPortfolios_thenExpect_them_in_DB() throws Exception {
        TestH2Initializer.initializeH2(h2CfgFile);
        dbTableMgr.insert(PortfolioSampleCfg.getPortfolioCompany2());
        dbTableMgr.insert(PortfolioSampleCfg.getPortfolioCompany3());

        List<Portfolio> portfolioList = dbTableMgr.selectPortfolioBy(1);
        assertEquals(2, portfolioList.size());
    }

    @Test
    public void testSetEnddates() throws IOException, SQLException {
        TestH2Initializer.initializeH2(h2CfgFile);
        dbTableMgr.insert(PortfolioSampleCfg.getPortfolioCompany1());
        dbTableMgr.insert(PortfolioSampleCfg.getPortfolioCompany3());
        dbTableMgr.updateEndDatesBy(1);
        List<Portfolio> l = dbTableMgr.selectPortfolioBy(1);
        assertThat(l.size(), is(1));
        Date endDate = l.get(0).pfl_end_dt;
        assertThat(endDate, is(IsNull.notNullValue()));
    }
}
