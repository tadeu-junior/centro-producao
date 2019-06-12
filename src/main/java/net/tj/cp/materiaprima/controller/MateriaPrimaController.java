package net.tj.cp.materiaprima.controller;

import java.net.URI;
import javax.annotation.Nullable;
import javax.validation.Valid;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import net.tj.cp.materiaprima.jpa.MateriaPrima;
import net.tj.cp.materiaprima.service.api.MateriaPrimaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller()
public class MateriaPrimaController {

	public static final String MATERIAS_PRIMAS = "/materiasprimas";
	public static final String LIMITE_MINIMO = "limiteMinimo";
	public static final String ID = "id";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final MateriaPrimaService materiaPrimaService;

	public MateriaPrimaController(MateriaPrimaService materiaPrimaServiceBean){
		this.materiaPrimaService = materiaPrimaServiceBean;
	}

	@Post(value = MateriaPrimaController.MATERIAS_PRIMAS, produces = MediaType.APPLICATION_JSON)
	public HttpResponse save(@Body @Valid MateriaPrima materiaPrima){
		materiaPrimaService.save(materiaPrima);
		logger.info(materiaPrima.toString());
		return HttpResponse.created(materiaPrima).headers(headers -> headers.location(createLocation(materiaPrima)));
	}

	private URI createLocation(MateriaPrima materiaPrima){
		return URI.create(MateriaPrimaController.MATERIAS_PRIMAS+'/'+materiaPrima.getId());
	}

	@Get(value = MateriaPrimaController.MATERIAS_PRIMAS+"{?"+MateriaPrimaController.LIMITE_MINIMO+"}", produces = MediaType.TEXT_PLAIN)
	public String searchMateriaPrima(@QueryValue(MateriaPrimaController.LIMITE_MINIMO) @Nullable Integer limiteMinimo){
		return materiaPrimaService.searchMateriaPrima(limiteMinimo);
	}

	@Delete(value = MateriaPrimaController.MATERIAS_PRIMAS+"/{"+MateriaPrimaController.ID+"}")
	public HttpResponse delete(Long id){
		materiaPrimaService.delete(id);
		return HttpResponse.noContent();
	}
}
