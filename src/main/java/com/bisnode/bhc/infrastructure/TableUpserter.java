package com.bisnode.bhc.infrastructure;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.bisnode.bhc.domain.Portfolio;
import org.hibernate.Session;

public class TableUpserter {

    private final HibernateAdapter hibernate;

    public TableUpserter(URL configFile) throws IOException {
        hibernate = new HibernateAdapter(configFile, Arrays.asList(Portfolio.class));
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
