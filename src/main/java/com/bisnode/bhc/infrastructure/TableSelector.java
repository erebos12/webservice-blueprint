package com.bisnode.bhc.infrastructure;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.util.List;

public class TableSelector {

    private EntityManagerFactory emf;
    private EntityManager em;

    public TableSelector(String persistanceUnit) throws IOException {
        emf = Persistence.createEntityManagerFactory(persistanceUnit);
    }

    public CriteriaBuilder createCriteriaBuiler() {
        em = emf.createEntityManager();
        return em.getCriteriaBuilder();
    }

    public <T> List<T> executeQuery(CriteriaQuery query) {
        try {
            return em.createQuery(query).getResultList();
        } finally {
            em.close();
        }
    }
}
