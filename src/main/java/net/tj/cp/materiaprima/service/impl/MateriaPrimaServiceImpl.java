package net.tj.cp.materiaprima.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Singleton;

import net.tj.cp.materiaprima.dao.api.MateriaPrimaDAO;
import net.tj.cp.materiaprima.jpa.MateriaPrima;
import net.tj.cp.materiaprima.service.api.MateriaPrimaService;

@Singleton
public class MateriaPrimaServiceImpl implements MateriaPrimaService {

	private MateriaPrimaDAO materiaPrimaDAO;

	public MateriaPrimaServiceImpl(MateriaPrimaDAO materiaPrimaDAO){
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
