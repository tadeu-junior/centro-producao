package net.tj.cp.materiaprima.dao.jpa;

import java.util.List;
import java.util.Objects;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.spring.tx.annotation.Transactional;
import net.tj.cp.materiaprima.dao.api.MateriaPrimaDAO;
import net.tj.cp.materiaprima.jpa.MateriaPrima;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MateriaPrimaDaoJPA implements MateriaPrimaDAO {

	@PersistenceContext
	private EntityManager entityManager;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public MateriaPrimaDaoJPA(@CurrentSession EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void save(@NotNull MateriaPrima materiaPrima) {
		entityManager.persist(materiaPrima);
		logger.info(materiaPrima.toString());
	}

	@Override
	@Transactional
	public void delete(@NotNull Long id) {
		entityManager.remove(entityManager.find(MateriaPrima.class, id));
	}

	@Override
	public int update(@NotNull MateriaPrima materiaPrima) {
		return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MateriaPrima> search() {
		return getMateriasPrimas(getSearchSql(null));
	}

	private List<MateriaPrima> getMateriasPrimas(String sql) {
		return entityManager.createQuery(sql, MateriaPrima.class).getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<MateriaPrima> search(Integer quantidadeMinima) {
		return getMateriasPrimas(getSearchSql(quantidadeMinima));
	}

	private String getSearchSql(Integer quantidadeMinima){
		if (Objects.isNull(quantidadeMinima)){
			return "SELECT mp FROM MateriaPrima as mp";
		}
		return "SELECT mp FROM MateriaPrima as mp WHERE mp.quantidade < "+quantidadeMinima;
	}

	@Override
	public List<MateriaPrima> searchById(@NotNull Integer id) {
		return null;
	}
}
