package com.bisnode.bhc.infrastructure;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class TableUpserter {

    private static final Logger logger = LoggerFactory.getLogger(TableUpserter.class);
    private final HibernateAdapter hibernate;

    public TableUpserter(URL configFile, List<Class<?>> entityClasses) throws IOException {
        hibernate = new HibernateAdapter(configFile, entityClasses);
    }

    public <T> void upsert(T object2Insert) {
        try (Session session = hibernate.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(object2Insert);
            session.getTransaction().commit();
            logger.info("upsert(): successfully upserted");
        }
    }

    public int updateAllEndDates() {
        try (Session session = hibernate.getSessionFactory().openSession()) {
            session.beginTransaction();
            String hqlUpdate = "update Portfolio p set p.pfl_end_dt = :currentDate";
            int updatedEntities = session.createQuery(hqlUpdate)
                    .setDate("currentDate", new Date())
                    .executeUpdate();
            session.getTransaction().commit();
            logger.info("updateAllEndDates(): nbr of updatedEntities: " + updatedEntities);
            return updatedEntities;
        }
    }
}
