package com.bisnode.bhc.infrastructure;

import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TableUpserter {

    private final HibernateAdapter hibernate;

    public TableUpserter(URL configFile, List<Class<?>> entityClasses) throws IOException {
        hibernate = new HibernateAdapter(configFile, entityClasses);
    }

    public <T> void upsert(T object2Insert) throws Exception {
        try (Session session = hibernate.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(object2Insert);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
}
