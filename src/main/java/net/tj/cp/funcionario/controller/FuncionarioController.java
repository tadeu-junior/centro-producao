package net.tj.cp.funcionario.controller;

import java.net.URI;
import javax.validation.Valid;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import net.tj.cp.funcionario.jpa.Funcionario;
import net.tj.cp.funcionario.service.api.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller()
public class FuncionarioController {

	private static final String FUNCIONARIO = "/funcionarios";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final FuncionarioService funcionarioService;

	public FuncionarioController(FuncionarioService funcionarioService){
		this.funcionarioService = funcionarioService;
	}

	@Post(value = FuncionarioController.FUNCIONARIO, produces = MediaType.APPLICATION_JSON)
	public HttpResponse save(@Body @Valid Funcionario funcionario){
		funcionarioService.save(funcionario);
		logger.info(funcionario.toString());
		return HttpResponse.created(funcionario).headers(headers -> headers.location(createLocation(funcionario)));
	}

	private URI createLocation(Funcionario funcionario){
		return URI.create(FuncionarioController.FUNCIONARIO+'/'+funcionario.getId());
	}

	@Put(value = FuncionarioController.FUNCIONARIO)
	public HttpResponse update (@Body @Valid Funcionario funcionario){
		funcionarioService.save(funcionario);
		logger.info(funcionario.toString());
		return HttpResponse.noContent().header(HttpHeaders.LOCATION, createLocation(funcionario).getPath());
	}





}
