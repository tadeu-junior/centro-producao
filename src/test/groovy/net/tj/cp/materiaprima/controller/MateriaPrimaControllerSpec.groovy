package net.tj.cp.materiaprima.controller

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import net.tj.cp.Application
import net.tj.cp.database.DataBaseApplication
import net.tj.cp.funcionario.controller.FuncionarioController
import net.tj.cp.funcionario.jpa.Funcionario
import net.tj.cp.materiaprima.jpa.MateriaPrima
import org.junit.jupiter.api.BeforeEach
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject

@MicronautTest(application = Application.class)
class MateriaPrimaControllerSpec extends Specification{

    @Inject
    @Client('/')
    RxHttpClient client

    @Inject
    DataBaseApplication dataBaseApplication

    @BeforeEach
    void cleanDataBase(){
        dataBaseApplication.truncateTable("MateriaPrima")
    }

    @Unroll
    void "inserir materia prima"() {
        given:
        dataBaseApplication.truncateTable("MATERIA_PRIMA")
        def nome = 'Fermento (Tipo 1)'
        def quantidade = 10
        def materiaPrimaFermento = createMateriaPrima(nome,quantidade)
        def request = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, materiaPrimaFermento)

        when:
        def resultado = client.toBlocking().exchange(request, MateriaPrima)

        then:
        nome == resultado.getBody().get().getNome()
        quantidade == resultado.getBody().get().getQuantidade()
        HttpStatus.CREATED == resultado.getStatus()
        MateriaPrimaController.MATERIAS_PRIMAS+'/1' == resultado.header(HttpHeaders.LOCATION)
    }

    private static MateriaPrima createMateriaPrima(String nome, Integer quantidade){
        def materiaPrima
        materiaPrima = new MateriaPrima()
        materiaPrima.setNome(nome)
        materiaPrima.setQuantidade(quantidade)
        return materiaPrima
    }

    @Unroll
    void "atualiza materia prima"() {
        given:
        dataBaseApplication.truncateTable("MATERIA_PRIMA")
        def materiaPrimaFermento = createMateriaPrima('Fermento (Tipo 1)',10)
        def requestInclusao = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, materiaPrimaFermento)

        when:
        client.toBlocking().exchange(requestInclusao, MateriaPrima)
        materiaPrimaFermento.setNome("Farinha")
        def requestAlteracao = HttpRequest.PUT(MateriaPrimaController.MATERIAS_PRIMAS, materiaPrimaFermento)
        def responseUpdate = client.toBlocking().exchange(requestAlteracao, MateriaPrima)

        then:
        HttpStatus.NO_CONTENT == responseUpdate.getStatus()
        MateriaPrimaController.MATERIAS_PRIMAS+'/3' == responseUpdate.header(HttpHeaders.LOCATION)
    }


    @Unroll
    void "lista materia prima sem filtro"() {
        given:
        dataBaseApplication.truncateTable("MATERIA_PRIMA")
        def requestFermento = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, createMateriaPrima('Fermento (Tipo 1)',10))
        def requestAcucar = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, createMateriaPrima('Açucar',4))

        when:
        client.toBlocking().exchange(requestFermento, MateriaPrima)
        client.toBlocking().exchange(requestAcucar, MateriaPrima)
        def lista = client.toBlocking().retrieve(HttpRequest.GET(MateriaPrimaController.MATERIAS_PRIMAS),String)

        then:
        def listaTeste = '4 - Fermento (Tipo 1) - Quantidade: 10\n' +
                '5 - Açucar - Quantidade: 4'
        listaTeste == lista
    }

    @Unroll
    void "lista materia prima quantidade menor 5"() {
        given:
        dataBaseApplication.truncateTable("MATERIA_PRIMA")
        def requestFermento = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, createMateriaPrima('Fermento (Tipo 1)',10))
        def requestAcucar = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, createMateriaPrima('Açucar',4))

        when:
        client.toBlocking().exchange(requestFermento, MateriaPrima)
        client.toBlocking().exchange(requestAcucar, MateriaPrima)
        def lista = client.toBlocking().retrieve(HttpRequest.GET(MateriaPrimaController.MATERIAS_PRIMAS+"?limiteMinimo=5"),String)

        then:
        def listaTeste = '7 - Açucar - Quantidade: 4'
        listaTeste == lista
    }

    @Unroll
    void "remover materia prima"() {
        given:
        dataBaseApplication.truncateTable("MATERIA_PRIMA")
        def requestFermento = HttpRequest.POST(MateriaPrimaController.MATERIAS_PRIMAS, createMateriaPrima('Fermento (Tipo 1)',10))

        when:
        def responseInsert = client.toBlocking().exchange(requestFermento, MateriaPrima)
        def requestDelete = HttpRequest.DELETE(responseInsert.header(HttpHeaders.LOCATION))
        def responseDelete = client.toBlocking().exchange(requestDelete, MateriaPrima)

        then:
        HttpStatus.NO_CONTENT == responseDelete.getStatus()
    }

}
