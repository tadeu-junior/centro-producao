package net.tj.cp.materiaprima.service.api;

import javax.inject.Singleton;

import net.tj.cp.materiaprima.dto.MateriaPrima;


@Singleton
public interface MateriaPrimaService {

	String searchMateriaPrimaQuantidadeMinima(Integer quantidadeMinima);

	MateriaPrima save(MateriaPrima materiaPrima);

	MateriaPrima update(MateriaPrima materiaPrima);

	void delete(Long id);
}
