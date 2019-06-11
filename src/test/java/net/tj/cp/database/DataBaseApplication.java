package net.tj.cp.database;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.spring.tx.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class DataBaseApplication {

	@PersistenceContext
	private EntityManager entityManager;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public DataBaseApplication(@CurrentSession EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public void truncateTable(String table){
		logger.info("TRUNCATE - " + table);
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE;").executeUpdate();
		entityManager.createNativeQuery("TRUNCATE TABLE "+table).executeUpdate();
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE;").executeUpdate();
	}
}
