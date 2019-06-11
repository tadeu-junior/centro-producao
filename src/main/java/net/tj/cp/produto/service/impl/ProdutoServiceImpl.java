package net.tj.cp.produto.service.impl;

import javax.inject.Singleton;

import net.tj.cp.funcionario.jpa.FuncionarioJPA;
import net.tj.cp.produto.dao.api.ProdutoDao;

import net.tj.cp.produto.dto.Produto;
import net.tj.cp.produto.jpa.ProdutoJpa;
import net.tj.cp.produto.service.api.ProdutoService;

@Singleton
public class ProdutoServiceImpl implements ProdutoService {

	private ProdutoDao produtoDao;

	public ProdutoServiceImpl(ProdutoDao produtoDao){
		this.produtoDao = produtoDao;
	}

	@Override
	public Produto save(Produto produto) {
		produto.setId(produtoDao.save(transformDtoToJpa(produto)).getId());
		return produto;
	}

	private ProdutoJpa transformDtoToJpa(Produto produto) {
		return ProdutoJpa
				.builder()
				.id(produto.getId())
				.nome(produto.getNome())
				.build();//Transform Funcionário e Material
	}

	@Override
	public Produto update(Produto produto) {
		produtoDao.update(transformDtoToJpa(produto));
		return produto;
	}

	@Override
	public Produto search(Long id) {
		final ProdutoJpa jpa = produtoDao.searchById(id);
		return Produto
				.builder()
				.id(jpa.getId())
				.nome(jpa.getNome())
				.build();
	}

	@Override
	public void delete(Long id) {
		produtoDao.delete(id);
	}

}
