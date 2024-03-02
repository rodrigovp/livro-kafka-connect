package br.com.alura.mspesquisa.service;

import br.com.alura.mspesquisa.dominio.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;

import static br.com.alura.mspesquisa.dominio.DateUtils.localDateTimeOf;
import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.cpf;
import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.umVoo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class SelecaoDeVoosTest {

    @Autowired
    private SelecaoDeVoos selecaoDeVoos;

    @Autowired
    private RepositorioDeNovosVoos repositorioDeNovosVoos;

    @MockBean
    private RepositorioDeVoosSelecionados repositorioDeVoosSelecionados;

    @Autowired
    private TransactionTemplate transactionTemplate;

    private Voo voo;
    private IdentificadorCompanhiaAerea idCompanhiaAerea;

    @BeforeEach
    void setUp() {
        idCompanhiaAerea = new IdentificadorCompanhiaAerea("identificador");
        voo = umVoo(idCompanhiaAerea);
        transactionTemplate.executeWithoutResult(status -> repositorioDeNovosVoos.save(voo));
    }

    @AfterEach
    void tearDown(){
        transactionTemplate.executeWithoutResult(status -> repositorioDeNovosVoos.delete(voo));
    }

    @Test
    void buscarVoosDisponiveis() {
        var origem = new Origem(Aeroporto.CGH, localDateTimeOf("2024-08-21T18:38:39"));

        assertThat(selecaoDeVoos.buscar(origem, Aeroporto.SDU)).contains(voo);
    }

    @Test
    void selecionarVooExistente(){
        VooSelecionado vooSelecionado = new VooSelecionado(voo, 1, 1, cpf());
        selecaoDeVoos.selecionar(vooSelecionado);

        verify(repositorioDeVoosSelecionados).save(vooSelecionado);
    }
}