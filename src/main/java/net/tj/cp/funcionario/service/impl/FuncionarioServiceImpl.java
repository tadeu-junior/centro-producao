package net.tj.cp.funcionario.service.impl;

import javax.inject.Singleton;

import net.tj.cp.funcionario.dao.api.FuncionarioDao;
import net.tj.cp.funcionario.dto.Funcionario;

import net.tj.cp.funcionario.jpa.FuncionarioJPA;
import net.tj.cp.funcionario.service.api.FuncionarioService;

@Singleton
public class FuncionarioServiceImpl implements FuncionarioService {

	private FuncionarioDao funcionarioDao;

	public FuncionarioServiceImpl(FuncionarioDao funcionarioDao){
		this.funcionarioDao = funcionarioDao;
	}

	@Override
	public Funcionario save(Funcionario funcionario) {
		funcionario.setId(funcionarioDao.save(transformDtoToJpa(funcionario)).getId());
		return funcionario;
	}

	private FuncionarioJPA transformDtoToJpa(Funcionario funcionario) {
		return FuncionarioJPA
				.builder()
				.id(funcionario.getId())
				.nome(funcionario.getNome())
				.jornada(funcionario.getJornada())
				.build();
	}

	@Override
	public Funcionario update(Funcionario funcionario) {
		funcionarioDao.update(transformDtoToJpa(funcionario));
		return funcionario;
	}

	@Override
	public void delete(Long id) {
		funcionarioDao.delete(id);
	}

	@Override
	public Funcionario search(Long id) {
		final FuncionarioJPA funcionarioJPA = funcionarioDao.searchById(id);
		return Funcionario
				.builder()
				.id(funcionarioJPA.getId())
				.nome(funcionarioJPA.getNome())
				.jornada(funcionarioJPA.getJornada())
				.build();
	}

}
