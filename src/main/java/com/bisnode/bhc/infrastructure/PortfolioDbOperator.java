package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.configuration.CfgParams;
import com.bisnode.bhc.domain.Portfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class PortfolioDbOperator {

    private TableUpserter tableUpserter;
    private TableSelector tableSelector;

    @Autowired
    public PortfolioDbOperator(CfgParams cfgParams) throws IOException {
        tableUpserter = new TableUpserter(cfgParams.persistence_unit);
        tableSelector = new TableSelector(cfgParams.persistence_unit);
    }

    public void insert(Portfolio portfolio) {
        tableUpserter.insert(portfolio);
    }

    public int updateEndDatesBy(Integer pfl_csg_id) {
        CriteriaBuilder cb = tableUpserter.createCriteriaBuiler();
        CriteriaUpdate update = cb.createCriteriaUpdate(Portfolio.class);
        Root root = update.from(Portfolio.class);
        update.set("pfl_end_dt", new Date());
        update.where(cb.and(cb.equal(root.get("pfl_csg_id"), pfl_csg_id),
                cb.isNull(root.get("pfl_end_dt"))));
        return tableUpserter.executeUpdate(update);
    }

    public List<Portfolio> selectPortfolioBy(Integer pfl_csg_id) {
        CriteriaBuilder cb = tableSelector.createCriteriaBuiler();
        CriteriaQuery<Portfolio> q = cb.createQuery(Portfolio.class);
        Root<Portfolio> e = q.from(Portfolio.class);
        q.where(cb.equal(e.get("pfl_csg_id"), pfl_csg_id));
        return tableSelector.executeQuery(q);
    }
}
