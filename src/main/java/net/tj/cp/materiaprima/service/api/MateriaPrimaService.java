package net.tj.cp.materiaprima.service.api;

import javax.inject.Singleton;

import net.tj.cp.materiaprima.jpa.MateriaPrima;

@Singleton
public interface MateriaPrimaService {

	String searchMateriaPrima(Integer quantidadeMinima);

	void save(MateriaPrima materiaPrima);

	void delete(Long id);
}
