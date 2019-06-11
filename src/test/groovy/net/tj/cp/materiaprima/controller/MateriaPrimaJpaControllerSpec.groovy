package net.tj.cp.materiaprima.controller

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import net.tj.cp.Application
import net.tj.cp.database.DataBaseApplication
import net.tj.cp.materiaprima.jpa.MateriaPrimaJpa
import org.junit.jupiter.api.BeforeEach
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject

@MicronautTest(application = Application.class)
class MateriaPrimaJpaControllerSpec extends Specification{

    @Inject
    @Client('/')
    RxHttpClient client

    @Inject
    DataBaseApplication dataBaseApplication

    @BeforeEach
    void cleanDataBase(){
        dataBaseApplication.truncateTable("MateriaPrimaJpa")
    }

    @Unroll
    void "inserir materia prima"() {
        given:
        dataBaseApplication.truncateTable("MATERIA_PRIMA")
        def nome = 'Fermento (Tipo 1)'
        def quantidade = 10
        def request = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, createMateriaPrima(nome,quantidade))

        when:
        def responseInsert = client.toBlocking().exchange(request, MateriaPrimaJpa)

        then:
        nome == responseInsert.getBody().get().getNome()
        quantidade == responseInsert.getBody().get().getQuantidade()
        HttpStatus.CREATED == responseInsert.getStatus()
        MateriaPrimaController.MATERIAS_PRIMAS+'/1' == responseInsert.header(HttpHeaders.LOCATION)
    }

    private static MateriaPrimaJpa createMateriaPrima(String nome, Integer quantidade){
        def materiaPrima
        materiaPrima = new MateriaPrimaJpa()
        materiaPrima.setNome(nome)
        materiaPrima.setQuantidade(quantidade)
        return materiaPrima
    }

    @Unroll
    void "atualiza materia prima"() {
        given:
        dataBaseApplication.truncateTable('MATERIA_PRIMA')
        def nome = 'Farinha'
        def requestInclusao = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, createMateriaPrima('Fermento (Tipo 1)',10))

        when:
        def responseInclusao = client.toBlocking().exchange(requestInclusao, MateriaPrimaJpa)
        def materiaPrimaIncluida = responseInclusao.getBody().get()
        materiaPrimaIncluida.setNome(nome)
        def requestAlteracao = HttpRequest.PUT(MateriaPrimaController.MATERIAS_PRIMAS, materiaPrimaIncluida)
        def responseUpdate = client.toBlocking().exchange(requestAlteracao, MateriaPrimaJpa)

        then:
        nome == responseUpdate.getBody().get().getNome()
        HttpStatus.OK == responseUpdate.getStatus()
        MateriaPrimaController.MATERIAS_PRIMAS+'/2' == responseUpdate.header(HttpHeaders.LOCATION)
    }


    @Unroll
    void "lista materia prima sem filtro"() {
        given:
        dataBaseApplication.truncateTable("MATERIA_PRIMA")
        def requestFermento = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, createMateriaPrima('Fermento (Tipo 1)',10))
        def requestAcucar = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, createMateriaPrima('Açucar',4))

        when:
        client.toBlocking().exchange(requestFermento, MateriaPrimaJpa)
        client.toBlocking().exchange(requestAcucar, MateriaPrimaJpa)
        def lista = client.toBlocking().retrieve(HttpRequest.GET(MateriaPrimaController.MATERIAS_PRIMAS),String)

        then:
        def listaTeste = '3 - Fermento (Tipo 1) - Quantidade: 10\n' +
                '4 - Açucar - Quantidade: 4'
        listaTeste == lista
    }

    @Unroll
    void "lista materia prima quantidade menor 5"() {
        given:
        dataBaseApplication.truncateTable("MATERIA_PRIMA")
        def requestFermento = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, createMateriaPrima('Fermento (Tipo 1)',10))
        def requestAcucar = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, createMateriaPrima('Açucar',4))

        when:
        client.toBlocking().exchange(requestFermento, MateriaPrimaJpa)
        client.toBlocking().exchange(requestAcucar, MateriaPrimaJpa)
        def lista = client.toBlocking().retrieve(HttpRequest.GET(MateriaPrimaController.MATERIAS_PRIMAS+"?limiteMinimo=5"),String)

        then:
        def listaTeste = '6 - Açucar - Quantidade: 4'
        listaTeste == lista
    }

    @Unroll
    void "remover materia prima"() {
        given:
        dataBaseApplication.truncateTable("MATERIA_PRIMA")
        def requestFermento = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, createMateriaPrima('Fermento (Tipo 1)',10))

        when:
        def responseInsert = client.toBlocking().exchange(requestFermento, MateriaPrimaJpa)
        def requestDelete = HttpRequest.DELETE(responseInsert.header(HttpHeaders.LOCATION))
        def responseDelete = client.toBlocking().exchange(requestDelete, MateriaPrimaJpa)

        then:
        HttpStatus.NO_CONTENT == responseDelete.getStatus()
    }

}
