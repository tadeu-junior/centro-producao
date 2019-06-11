package net.tj.cp.materiaprima.service.impl;

import java.util.stream.Collectors;
import javax.inject.Singleton;

import net.tj.cp.materiaprima.dao.api.MateriaPrimaDao;

import net.tj.cp.materiaprima.dto.MateriaPrima;
import net.tj.cp.materiaprima.jpa.MateriaPrimaJpa;
import net.tj.cp.materiaprima.service.api.MateriaPrimaService;

@Singleton
public class MateriaPrimaServiceImpl implements MateriaPrimaService {

	private MateriaPrimaDao materiaPrimaDAO;

	public MateriaPrimaServiceImpl(MateriaPrimaDao materiaPrimaDAO){
		this.materiaPrimaDAO = materiaPrimaDAO;
	}

	@Override
	public MateriaPrima save(MateriaPrima materiaPrima) {
		materiaPrima.setId(materiaPrimaDAO.save(transformDtoToJpa(materiaPrima)).getId());
		return materiaPrima;
	}

	private MateriaPrimaJpa transformDtoToJpa(MateriaPrima materiaPrima) {
		return MateriaPrimaJpa
				.builder()
				.id(materiaPrima.getId())
				.nome(materiaPrima.getNome())
				.quantidade(materiaPrima.getQuantidade())
				.build();
	}

	@Override
	public MateriaPrima update(MateriaPrima materiaPrima) {
		materiaPrimaDAO.update(transformDtoToJpa(materiaPrima));
		return materiaPrima;
	}

	@Override
	public String searchMateriaPrimaQuantidadeMinima(Integer quantidadeMinima) {
		return materiaPrimaDAO.search(quantidadeMinima).stream().map(mp -> mp.toString()).collect(Collectors.joining("\n"));
	}

	@Override
	public void delete(Long id) {
		materiaPrimaDAO.delete(id);
	}
}
