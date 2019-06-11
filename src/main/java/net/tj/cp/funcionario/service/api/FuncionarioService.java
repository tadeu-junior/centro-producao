package net.tj.cp.funcionario.service.api;

import javax.inject.Singleton;

import net.tj.cp.funcionario.dto.Funcionario;

@Singleton
public interface FuncionarioService {

	Funcionario save(Funcionario funcionario);

	Funcionario update (Funcionario funcionario);

	void delete(Long id);

	Funcionario search(Long id);
}
