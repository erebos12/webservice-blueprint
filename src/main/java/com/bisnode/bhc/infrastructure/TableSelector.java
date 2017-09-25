package com.bisnode.bhc.infrastructure;

import com.bisnode.bhc.configuration.CfgParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Component
public class TableSelector extends TableMgrBase {

    private EntityManager em;

    @Autowired
    public TableSelector(CfgParams cfgParams) throws IOException, SQLException {
        super(cfgParams);
    }

    public CriteriaBuilder createCriteriaBuilder() {
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
