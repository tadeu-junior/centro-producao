package net.tj.cp.produto.service.api;

import javax.inject.Singleton;

import net.tj.cp.produto.dto.Produto;

@Singleton
public interface ProdutoService {

	Produto search(Long id);

	Produto save(Produto produto);

	Produto update(Produto produto);

	void delete(Long id);
}
