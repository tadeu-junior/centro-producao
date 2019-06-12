package net.tj.cp.funcionario.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import net.tj.cp.funcionario.dao.api.FuncionarioDao;
import net.tj.cp.funcionario.jpa.Funcionario;
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
	public void save(@NotNull Funcionario funcionario) {
		entityManager.persist(funcionario);
		logger.info(funcionario.toString());
	}

	@Override
	public void delete(@NotNull Long id) {

	}

	@Override
	public int update(@NotNull Funcionario funcionario) {
		return 0;
	}

	@Override
	public Funcionario searchById(Long id) {
		return null;
	}
}
