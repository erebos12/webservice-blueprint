package com.bisnode.bhc.infrastructure;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.bisnode.bhc.domain.Portfolio;
import org.hibernate.Session;

public class TableInserter {

	private final HibernateAdapter hibernate;

	public TableInserter(URL configFile) throws IOException {
		hibernate = new HibernateAdapter(configFile, Arrays.asList(Portfolio.class));
	}

	public <T> void insertInto(List<T> listToInsert) throws Exception {
		try (Session session = hibernate.getSessionFactory().openSession()) {
			session.beginTransaction();
			listToInsert.stream().forEach(typeElement -> session.save(typeElement));
			session.getTransaction().commit();
		} catch (Exception e) {			
			throw e;
		}
	}
}
