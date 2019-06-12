package net.tj.cp.funcionario.dao.api;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import net.tj.cp.funcionario.jpa.Funcionario;

@Singleton
public interface FuncionarioDao {

	void save(@NotNull Funcionario funcionario);

	void delete(@NotNull Long id);

	int update(@NotNull Funcionario funcionario);

	Funcionario searchById(Long id);

}
