package net.tj.cp.produto

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import net.tj.cp.Application
import net.tj.cp.database.DataBaseApplication
import net.tj.cp.produto.controller.ProdutoController
import net.tj.cp.produto.jpa.ProdutoJpa
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject

@MicronautTest(application = Application.class)
class ProdutoControllerSpec extends Specification{


    @Inject
    @Client('/')
    RxHttpClient client

    @Inject
    DataBaseApplication dataBaseApplication

    @Unroll
    void "insere produto"() {
        given:
        dataBaseApplication.truncateTable("PRODUTO")
        def nome = 'Macarrão instantâneo'
        def produto = createProduto(nome)
        def requestInclusao = HttpRequest.POST(ProdutoController.PRODUTOS, produto)

        when:
        def responseInclusao = client.toBlocking().exchange(requestInclusao, ProdutoJpa)

        then:
        nome == responseInclusao.getBody().get().getNome()
        HttpStatus.CREATED == responseInclusao.getStatus()
        ProdutoController.PRODUTOS+'/1' == responseInclusao.header(HttpHeaders.LOCATION)
    }

    private static ProdutoJpa createProduto(String nome){
        def produto
        produto = new ProdutoJpa()
        produto.setNome(nome)
        return produto
    }

    @Unroll
    void "atualiza produto"() {
        given:
        dataBaseApplication.truncateTable("PRODUTO")
        def nome = 'Macarrão integral'
        def produto = createProduto(nome)
        def requestInclusao = HttpRequest.POST(ProdutoController.PRODUTOS, createProduto('Macarrão instantâneo'))
        def protudoIncluido

        when:
        def responseInclusao = client.toBlocking().exchange(requestInclusao, ProdutoJpa)
        protudoIncluido = responseInclusao.getBody().get()
        protudoIncluido.setNome(nome)
        def requestAlteracao = HttpRequest.PUT(ProdutoController.PRODUTOS, protudoIncluido)
        def responseUpdate = client.toBlocking().exchange(requestAlteracao, ProdutoJpa)

        then:
        nome == responseUpdate.getBody().get().getNome()
        HttpStatus.OK == responseUpdate.getStatus()
        ProdutoController.PRODUTOS+'/2' == responseUpdate.header(HttpHeaders.LOCATION)
    }

}
