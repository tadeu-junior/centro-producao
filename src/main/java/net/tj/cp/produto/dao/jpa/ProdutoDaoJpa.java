package net.tj.cp.produto.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import net.tj.cp.produto.dao.api.ProdutoDao;
import net.tj.cp.produto.jpa.ProdutoJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProdutoDaoJpa implements ProdutoDao {

	@PersistenceContext
	private EntityManager entityManager;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ProdutoDaoJpa(@CurrentSession EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public ProdutoJpa save(@NotNull ProdutoJpa produto) {
		entityManager.persist(produto);
		logger.info(produto.toString());
		return produto;
	}

	@Override
	@Transactional
	public void delete(@NotNull Long id) {
		entityManager.remove(entityManager.find(ProdutoJpa.class, id));
	}

	@Override
	@Transactional
	public ProdutoJpa update(@NotNull ProdutoJpa produto) {
		return entityManager.merge(produto);
	}

	@Override
	@Transactional
	public ProdutoJpa searchById(Long id) {
		return entityManager.find(ProdutoJpa.class, id);
	}
}
