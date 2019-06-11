package net.tj.cp.funcionario.dao.api;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import net.tj.cp.funcionario.jpa.FuncionarioJPA;

@Singleton
public interface FuncionarioDao {

	FuncionarioJPA save(@NotNull FuncionarioJPA funcionario);

	void delete(@NotNull Long id);

	FuncionarioJPA update(@NotNull FuncionarioJPA funcionario);

	FuncionarioJPA searchById(Long id);

}
