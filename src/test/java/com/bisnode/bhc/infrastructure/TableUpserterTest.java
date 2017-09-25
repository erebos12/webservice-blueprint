package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.Portfolio;
import com.bisnode.bhc.utils.H2DbInitializer;
import com.bisnode.bhc.utils.PortfolioSampleCfg;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TableUpserterTest {

    private static TableSelector tableSelector;
    private static TableUpserter tableUpserter;

    @BeforeClass
    public static void setup() throws SQLException, RuntimeException, IOException {
        H2DbInitializer.initializeH2();
        String persistence_unit = "portfolio_test";
        tableUpserter = new TableUpserter(persistence_unit);
        tableSelector = new TableSelector(persistence_unit);
    }

    @Test
    public void test() throws IOException, SQLException {
        tableUpserter.insert(PortfolioSampleCfg.getPortfolioCompany2());
        int res = tableUpserter.executeUpdate(createUpdate());
        assertThat(res, is(1));
        List<Portfolio> list = tableSelector.executeQuery(createQuery(1));
        assertThat(list.size(), is(1));
        assertThat(list.get(0).pfl_end_dt, is(IsNull.notNullValue()));
    }

    private CriteriaUpdate createUpdate() {
        CriteriaBuilder cb = tableUpserter.createCriteriaBuiler();
        CriteriaUpdate update = cb.createCriteriaUpdate(Portfolio.class);
        Root root = update.from(Portfolio.class);
        update.set("pfl_end_dt", new Date());
        return update.where(cb.and(cb.equal(root.get("pfl_csg_id"), 1),
                cb.isNull(root.get("pfl_end_dt"))));
    }


    private CriteriaQuery createQuery(Integer pfl_csg_id) {
        CriteriaBuilder cb = tableSelector.createCriteriaBuiler();
        CriteriaQuery<Portfolio> q = cb.createQuery(Portfolio.class);
        Root<Portfolio> e = q.from(Portfolio.class);
        return q.where(cb.equal(e.get("pfl_csg_id"), pfl_csg_id));
    }
}