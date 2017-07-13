package com.bisnode.bhc.infrastructure;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateAdapter implements AutoCloseable {

	private static final Logger logger = LoggerFactory.getLogger(HibernateAdapter.class);

	private final SessionFactory sessionFactory;
	private final StandardServiceRegistry serviceRegistry;

	public HibernateAdapter(URL configFile, List<Class<?>> entityClasses) throws IOException {
		logger.info("Reading configuration from {}", configFile);
		Configuration configuration = new Configuration().configure(configFile);
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		entityClasses.stream().forEach(e -> configuration.addAnnotatedClass(e));
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void close() {
		sessionFactory.close();
	}
}
