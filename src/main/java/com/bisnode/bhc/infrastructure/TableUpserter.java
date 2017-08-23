package com.bisnode.bhc.infrastructure;

import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class TableUpserter {

    private final HibernateAdapter hibernate;

    public TableUpserter(URL configFile, List<Class<?>> entityClasses) throws IOException {
        hibernate = new HibernateAdapter(configFile, entityClasses);
    }

    public <T> void upsert(T object2Insert){
        try (Session session = hibernate.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(object2Insert);
            session.getTransaction().commit();
        }
    }

    public int updateAllEndDates() {
        try (Session session = hibernate.getSessionFactory().openSession()) {
            session.beginTransaction();
            Date currentDate = new Date();
            String hqlUpdate = "update Portfolio p set p.PFL_END_DATE = :currentDate";
            int updatedEntities = session.createQuery(hqlUpdate)
                    .setDate("currentDate", currentDate)
                    .executeUpdate();
            session.getTransaction().commit();
            return updatedEntities;
        }
    }
}
