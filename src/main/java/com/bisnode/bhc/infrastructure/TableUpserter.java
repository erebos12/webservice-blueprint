package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.configuration.CfgParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import java.io.IOException;
import java.sql.SQLException;

@Component
public class TableUpserter extends TableMgrBase {

    private static final Logger logger = LoggerFactory.getLogger(TableUpserter.class);
    private EntityManager em;

    @Autowired
    public TableUpserter(CfgParams cfgParams) throws IOException, SQLException {
        super(cfgParams);
    }

    public CriteriaBuilder createCriteriaBuilder() {
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

    public <T> void insert(T object2insert) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object2insert);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
