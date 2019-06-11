package net.tj.cp.materiaprima.dao.api;

import java.util.List;

import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import net.tj.cp.materiaprima.jpa.MateriaPrima;

@Singleton
public interface MateriaPrimaDAO {

	void save(@NotNull MateriaPrima materiaPrima);

	void delete(@NotNull Long id);

	int update(@NotNull MateriaPrima materiaPrima);

	List<MateriaPrima> search();

	List<MateriaPrima> search(Integer quantidadeMinima);

	List<MateriaPrima> searchById(@NotNull Integer id);

}
