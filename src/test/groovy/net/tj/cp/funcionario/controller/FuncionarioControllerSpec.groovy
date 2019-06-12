package net.tj.cp.funcionario.controller

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import net.tj.cp.Application
import net.tj.cp.database.DataBaseApplication
import net.tj.cp.funcionario.jpa.Funcionario
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject

@MicronautTest(application = Application.class)
class FuncionarioControllerSpec extends Specification{

    @Inject
    @Client('/')
    RxHttpClient client

    @Inject
    DataBaseApplication dataBaseApplication

    @Unroll
    void "insere funcionario"() {
        given:
        dataBaseApplication.truncateTable("FUNCIONARIO")
        def nome = 'Cebolinha'
        def jornada = 8
        def funcionario = createFuncionario(nome,jornada)
        def requestFuncionario = HttpRequest.POST(FuncionarioController.FUNCIONARIO, funcionario)

        when:
        def response = client.toBlocking().exchange(requestFuncionario, Funcionario)

        then:
        nome == response.getBody().get().getNome()
        jornada == response.getBody().get().getJornada()
        HttpStatus.CREATED == response.getStatus()
        FuncionarioController.FUNCIONARIO+'/1' == response.header(HttpHeaders.LOCATION)
    }

    private static Funcionario createFuncionario(String nome, Integer jornada){
        def funcionario
        funcionario = new Funcionario()
        funcionario.setNome(nome)
        funcionario.setJornada(jornada)
        return funcionario
    }

    @Unroll
    void "atualiza funcionario"() {
        given:
        dataBaseApplication.truncateTable("FUNCIONARIO")
        def funcionario = createFuncionario('Monica',4)
        def requestInclusao = HttpRequest.POST(FuncionarioController.FUNCIONARIO, funcionario)

        when:
        client.toBlocking().exchange(requestInclusao, Funcionario)
        funcionario.setNome("Monica Dentuça")
        def requestAlteracao = HttpRequest.PUT(FuncionarioController.FUNCIONARIO, funcionario)
        def responseUpdate = client.toBlocking().exchange(requestAlteracao, Funcionario)

        then:
        HttpStatus.NO_CONTENT == responseUpdate.getStatus()
        FuncionarioController.FUNCIONARIO+'/3' == responseUpdate.header(HttpHeaders.LOCATION)
    }

    @Unroll
    void "remove funcionario"() {
        given:
        dataBaseApplication.truncateTable("FUNCIONARIO")
        def requestInclusao = HttpRequest.POST(FuncionarioController.FUNCIONARIO, createFuncionario('Monica',4))

        when:
        def responseInclude = client.toBlocking().exchange(requestInclusao, Funcionario)
        def requestRemocao = HttpRequest.DELETE(responseInclude.header(HttpHeaders.LOCATION))

        then:
        HttpStatus.NO_CONTENT == requestRemocao.getStatus()
        FuncionarioController.FUNCIONARIO+'/4' == requestRemocao.header(HttpHeaders.LOCATION)
    }

}
