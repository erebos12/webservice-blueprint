package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.Portfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.Date;

@Component
public class PortfolioTableUpserter {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioTableUpserter.class);
    private static final String PERSISTANCE_UNIT = "portfolio";
    private EntityManagerFactory emf;
    private EntityManager em;
    private CriteriaBuilder cb;

    public PortfolioTableUpserter() throws IOException {
        emf = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT);
        em = emf.createEntityManager();
        cb = em.getCriteriaBuilder();
    }

    public void insert(Portfolio portfolio) {
        em.getTransaction().begin();
        em.persist(portfolio);
        em.getTransaction().commit();
    }

    public int update(Integer pfl_csg_id) {
        CriteriaUpdate<Portfolio> update = cb.createCriteriaUpdate(Portfolio.class);
        Root e = update.from(Portfolio.class);
        update.set("pfl_end_dt", new Date());
        update.where(cb.equal(e.get("pfl_end_dt"), null))
                .where(cb.equal(e.get("pfl_csg_id"), pfl_csg_id));
        em.getTransaction().begin();
        int result = em.createQuery(update).executeUpdate();
        em.getTransaction().commit();
        return result;
    }
}
