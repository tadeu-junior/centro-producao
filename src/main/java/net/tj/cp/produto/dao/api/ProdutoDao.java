package net.tj.cp.produto.dao.api;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import net.tj.cp.produto.jpa.ProdutoJpa;

@Singleton
public interface ProdutoDao {

	ProdutoJpa save(@NotNull ProdutoJpa produto);

	void delete(@NotNull Long id);

	ProdutoJpa update(@NotNull ProdutoJpa produto);

	ProdutoJpa searchById(Long id);

}
