package net.tj.cp.materiaprima.dao.api;

import java.util.List;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import net.tj.cp.materiaprima.jpa.MateriaPrimaJpa;

@Singleton
public interface MateriaPrimaDao {

	MateriaPrimaJpa save(@NotNull MateriaPrimaJpa materiaPrimaJpa);

	void delete(@NotNull Long id);

	MateriaPrimaJpa update(@NotNull MateriaPrimaJpa materiaPrimaJpa);

	List<MateriaPrimaJpa> search();

	List<MateriaPrimaJpa> search(Integer quantidadeMinima);

	MateriaPrimaJpa searchById(Long id);

}
