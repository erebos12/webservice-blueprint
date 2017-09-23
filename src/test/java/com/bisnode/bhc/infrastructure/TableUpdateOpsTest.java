package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.utils.PortfolioSampleCfg;
import com.bisnode.bhc.utils.TestH2Initializer;
import com.google.common.io.Resources;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TableUpdateOpsTest {

    private static DbTableMgr dbTableMgr = null;
    private static final String h2TestDataFile = "bhc-data-h2.sql";
    private static final String h2CfgFile = Resources.getResource(h2TestDataFile).getFile();
    private TableUpdateOps tableUpdateOps;

    @Before
    public void setup() throws SQLException, RuntimeException, IOException {
        dbTableMgr = new DbTableMgr();
        TestH2Initializer.initializeH2(h2CfgFile);
        tableUpdateOps = new TableUpdateOps("portfolio");
    }

    @Test
    public void test() throws IOException, SQLException {
        dbTableMgr.insert(PortfolioSampleCfg.getPortfolioCompany2());

        CriteriaBuilder cb = tableUpdateOps.createCriteriaBuiler();

        CriteriaUpdate update = cb.createCriteriaUpdate(Portfolio.class);
        Root root = update.from(Portfolio.class);
        update.set("pfl_end_dt", new Date());
        update.where(cb.and(cb.equal(root.get("pfl_csg_id"), 1),
                cb.isNull(root.get("pfl_end_dt"))));

        int res = tableUpdateOps.executeUpdate(update);
        assertThat(res, is(1));
        List<Portfolio> list = dbTableMgr.selectPortfolioBy(1);
        assertThat(list.size(), is(1));
        assertThat(list.get(0).pfl_end_dt, is(IsNull.notNullValue()));
    }
}
