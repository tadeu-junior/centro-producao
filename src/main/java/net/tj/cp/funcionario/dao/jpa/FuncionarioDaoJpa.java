package net.tj.cp.funcionario.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import net.tj.cp.funcionario.dao.api.FuncionarioDao;
import net.tj.cp.funcionario.jpa.FuncionarioJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FuncionarioDaoJpa implements FuncionarioDao {

	@PersistenceContext
	private EntityManager entityManager;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public FuncionarioDaoJpa(@CurrentSession EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public FuncionarioJPA save(@NotNull FuncionarioJPA funcionario) {
		entityManager.persist(funcionario);
		logger.info(funcionario.toString());
		return funcionario;
	}

	@Override
	@Transactional
	public void delete(@NotNull Long id) {
		final FuncionarioJPA funcionario = entityManager.find(FuncionarioJPA.class, id);
		logger.info(funcionario.toString());
		entityManager.remove(funcionario);
	}

	@Override
	@Transactional
	public FuncionarioJPA update(@NotNull FuncionarioJPA funcionario) {
		logger.info(funcionario.toString());
		funcionario = searchById(funcionario.getId());
		return entityManager.merge(funcionario);
	}

	@Override
	public FuncionarioJPA searchById(Long id) {
		final FuncionarioJPA funcionario = entityManager.find(FuncionarioJPA.class, id);
		logger.info(funcionario.toString());
		return funcionario;
	}
}
