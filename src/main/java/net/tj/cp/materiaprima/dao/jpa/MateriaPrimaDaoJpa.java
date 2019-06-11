package net.tj.cp.materiaprima.dao.jpa;

import java.util.List;
import java.util.Objects;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import net.tj.cp.materiaprima.dao.api.MateriaPrimaDao;
import net.tj.cp.materiaprima.jpa.MateriaPrimaJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MateriaPrimaDaoJpa implements MateriaPrimaDao {

	@PersistenceContext
	private EntityManager entityManager;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public MateriaPrimaDaoJpa(@CurrentSession EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public MateriaPrimaJpa save(@NotNull MateriaPrimaJpa materiaPrima) {
		entityManager.persist(materiaPrima);
		logger.info(materiaPrima.toString());
		return materiaPrima;
	}

	@Override
	@Transactional
	public void delete(@NotNull Long id) {
		final MateriaPrimaJpa materiaPrimaJpa = searchById(id);
		logger.info(materiaPrimaJpa.toString());
		entityManager.remove(materiaPrimaJpa);
	}

	@Override
	@Transactional
	public MateriaPrimaJpa update(@NotNull MateriaPrimaJpa materiaPrima) {
		logger.info(materiaPrima.toString());
		materiaPrima = searchById(materiaPrima.getId());
		return entityManager.merge(materiaPrima);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MateriaPrimaJpa> search() {
		return getMateriasPrimas(getSearchSql(null));
	}

	private List<MateriaPrimaJpa> getMateriasPrimas(String sql) {
		return entityManager.createQuery(sql, MateriaPrimaJpa.class).getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<MateriaPrimaJpa> search(Integer quantidadeMinima) {
		return getMateriasPrimas(getSearchSql(quantidadeMinima));
	}

	private String getSearchSql(Integer quantidadeMinima){
		if (Objects.isNull(quantidadeMinima)){
			return "SELECT mp FROM MateriaPrimaJpa as mp";
		}
		return "SELECT mp FROM MateriaPrimaJpa as mp WHERE mp.quantidade < "+quantidadeMinima;
	}

	@Override
	public MateriaPrimaJpa searchById(Long id) {
		final MateriaPrimaJpa materiaPrimaJpa = entityManager.find(MateriaPrimaJpa.class, id);
		logger.info(materiaPrimaJpa.toString());
		return materiaPrimaJpa;
	}
}
