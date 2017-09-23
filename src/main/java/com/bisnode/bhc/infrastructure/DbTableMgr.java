package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.domain.Portfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class DbTableMgr {

    private static final Logger logger = LoggerFactory.getLogger(DbTableMgr.class);
    private static final String PERSISTANCE_UNIT = "portfolio";
    private EntityManagerFactory emf;
    private CriteriaBuilder cb;
    private TableUpdateOps tableUpdateOps;

    public DbTableMgr() throws IOException {
        emf = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT);
        tableUpdateOps = new TableUpdateOps(PERSISTANCE_UNIT);
    }

    public void insert(Portfolio portfolio) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(portfolio);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public int updateEndDatesBy(Integer pfl_csg_id) {
        CriteriaBuilder cb = tableUpdateOps.createCriteriaBuiler();
        CriteriaUpdate update = cb.createCriteriaUpdate(Portfolio.class);
        Root root = update.from(Portfolio.class);
        update.set("pfl_end_dt", new Date());
        update.where(cb.and(cb.equal(root.get("pfl_csg_id"), pfl_csg_id),
                cb.isNull(root.get("pfl_end_dt"))));
        return tableUpdateOps.executeUpdate(update);
    }

    public List<Portfolio> selectPortfolioBy(Integer pfl_csg_id) {
        EntityManager em = emf.createEntityManager();
        try {
            cb = em.getCriteriaBuilder();
            CriteriaQuery<Portfolio> q = cb.createQuery(Portfolio.class);
            Root<Portfolio> e = q.from(Portfolio.class);
            q.where(cb.equal(e.get("pfl_csg_id"), pfl_csg_id));
            return em.createQuery(q).getResultList();
        }
        finally {
            em.close();
        }
    }
}
