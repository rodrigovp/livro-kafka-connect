package br.com.alura.mspesquisa.dominio;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;

import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.cpf;
import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.umVoo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RepositorioDeVoosSelecionadosTest {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private RepositorioDeVoosSelecionados repositorioDeVoosSelecionados;

    private VooSelecionado vooSelecionado;

    @BeforeEach
    void setUp(){
        vooSelecionado = new VooSelecionado(umVoo(new IdentificadorCompanhiaAerea("identificador")), 3, 3, cpf());
        transactionTemplate.executeWithoutResult(status -> {
            repositorioDeVoosSelecionados.save(vooSelecionado);
        });
    }

    @AfterEach
    void tearDown(){
        transactionTemplate.executeWithoutResult(status -> {
            repositorioDeVoosSelecionados.delete(vooSelecionado);
        });
    }

    @Test
    void persistir(){
        assertThat(repositorioDeVoosSelecionados.findAll()).contains(vooSelecionado);
    }
}