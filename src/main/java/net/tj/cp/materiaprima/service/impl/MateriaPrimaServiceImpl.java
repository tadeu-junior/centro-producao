package net.tj.cp.materiaprima.service.impl;

import java.util.stream.Collectors;
import javax.inject.Singleton;

import net.tj.cp.materiaprima.dao.api.MateriaPrimaDao;
import net.tj.cp.materiaprima.jpa.MateriaPrima;
import net.tj.cp.materiaprima.service.api.MateriaPrimaService;

@Singleton
public class MateriaPrimaServiceImpl implements MateriaPrimaService {

	private MateriaPrimaDao materiaPrimaDAO;

	public MateriaPrimaServiceImpl(MateriaPrimaDao materiaPrimaDAO){
		this.materiaPrimaDAO = materiaPrimaDAO;
	}

	@Override
	public String searchMateriaPrima(Integer quantidadeMinima) {
		return materiaPrimaDAO.search(quantidadeMinima).stream().map(mp -> mp.toString()).collect(Collectors.joining("\n"));
	}

	@Override
	public void save(MateriaPrima materiaPrima) {
		materiaPrimaDAO.save(materiaPrima);
	}

	@Override
	public void delete(Long id) {
		materiaPrimaDAO.delete(id);
	}
}
