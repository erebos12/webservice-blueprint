package com.bisnode.bhc.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import java.io.IOException;

public class TableUpdateOps {

    private static final Logger logger = LoggerFactory.getLogger(DbTableMgr.class);
    private EntityManagerFactory emf;
    private EntityManager em;

    public TableUpdateOps(String persistanceUnit) throws IOException {
        emf = Persistence.createEntityManagerFactory(persistanceUnit);
    }

    public CriteriaBuilder createCriteriaBuiler() {
        em = emf.createEntityManager();
        return em.getCriteriaBuilder();
    }

    public int executeUpdate(CriteriaUpdate update) {
        try {
            em.getTransaction().begin();
            int result = em.createQuery(update).executeUpdate();
            em.getTransaction().commit();
            logger.info("executeUpdate: {}", result);
            return result;
        } finally {
            em.close();
        }
    }
}
