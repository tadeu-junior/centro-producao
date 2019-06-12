package net.tj.cp.funcionario.service.api;

import javax.inject.Singleton;

import net.tj.cp.funcionario.jpa.Funcionario;

@Singleton
public interface FuncionarioService {

	Funcionario search(Long id);

	void save(Funcionario funcionario);

	void delete(Long id);
}
