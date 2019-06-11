package net.tj.cp.produto.controller;

import java.net.URI;
import javax.validation.Valid;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import net.tj.cp.produto.dto.Produto;

import net.tj.cp.produto.service.api.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller()
public class ProdutoController {

	private static final String PRODUTOS = "/produtos";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ProdutoService produtoService;

	public ProdutoController(ProdutoService produtoService){
		this.produtoService = produtoService;
	}

	@Post(value = ProdutoController.PRODUTOS, produces = MediaType.APPLICATION_JSON)
	public HttpResponse save(@Body @Valid Produto produto){
		produtoService.save(produto);
		logger.info(produto.toString());
		return HttpResponse.created(produto).headers(headers -> headers.location(createLocation(produto)));
	}

	private URI createLocation(Produto produto){
		return URI.create(ProdutoController.PRODUTOS+'/'+produto.getId());
	}

	@Put(value = ProdutoController.PRODUTOS)
	public HttpResponse update (@Body @Valid Produto produto){
		produtoService.update(produto);
		logger.info(produto.toString());
		return HttpResponse.ok(produto).header(HttpHeaders.LOCATION, createLocation(produto).getPath());
	}

}
