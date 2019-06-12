package net.tj.cp.funcionario.service.impl;

import javax.inject.Singleton;

import net.tj.cp.funcionario.dao.api.FuncionarioDao;
import net.tj.cp.funcionario.jpa.Funcionario;
import net.tj.cp.funcionario.service.api.FuncionarioService;

@Singleton
public class FuncionarioServiceImpl implements FuncionarioService {

	private FuncionarioDao funcionarioDAO;

	public FuncionarioServiceImpl(FuncionarioDao funcionarioDAO){
		this.funcionarioDAO = funcionarioDAO;
	}

	@Override
	public Funcionario search(Long id) {
		return null;
	}

	@Override
	public void save(Funcionario funcionario) {
		funcionarioDAO.save(funcionario);
	}

	@Override
	public void delete(Long id) {
		funcionarioDAO.delete(id);
	}
}
