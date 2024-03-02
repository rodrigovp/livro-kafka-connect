package br.com.alura.mspesquisa.dominio;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.util.Optional;

import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.umVoo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RepositorioDeNovosVoosTest {

    @Autowired
    private RepositorioDeNovosVoos repositorioDeNovosVoos;
    private Voo voo;
    private IdentificadorCompanhiaAerea idCompanhiaAerea;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    void setUp() {
        idCompanhiaAerea = new IdentificadorCompanhiaAerea("identificador");
        voo = umVoo(idCompanhiaAerea);

        transactionTemplate.executeWithoutResult(status -> {
            repositorioDeNovosVoos.save(voo);
        });

    }

    @AfterEach
    void tearDown(){
        transactionTemplate.executeWithoutResult(status -> {
            repositorioDeNovosVoos.delete(voo);
        });
    }

    @Test
    void persistirUmNovoVoo() {
        assertThat(repositorioDeNovosVoos.findAll()).contains(voo);
    }

    @Test
    void removerUmVoo() {
        transactionTemplate.executeWithoutResult(status -> {
            repositorioDeNovosVoos.deleteByIdCompanhiaAerea(idCompanhiaAerea);
        });

        assertThat(repositorioDeNovosVoos.findAll()).doesNotContain(voo);
    }

    @Test
    void buscarVooPorIdCompanhiaAerea(){
        assertThat(repositorioDeNovosVoos.findByIdCompanhiaAerea(idCompanhiaAerea))
                .isEqualTo(Optional.of(voo));
    }

    @Test
    void buscarENaoEncontrarPorIdCompanhiaAerea(){
        assertThat(repositorioDeNovosVoos
                .findByIdCompanhiaAerea(new IdentificadorCompanhiaAerea("idCompanhiaAerea")))
                .isEqualTo(Optional.empty());
    }

    @Test
    void buscarVoosDisponiveis() {
        LocalDate dataDoVoo = LocalDate.of(2024, 8, 21);
        assertThat(repositorioDeNovosVoos
                .findByOrigemAeroportoAndOrigemPartidaBetweenAndDestinoAeroporto(
                        Aeroporto.CGH,
                        dataDoVoo.atStartOfDay(),
                        dataDoVoo.plusDays(1).atStartOfDay(),
                        Aeroporto.SDU
                )).contains(voo);
    }

    @Test
    void buscarVooDisponivelSemSucesso(){
        LocalDate dataDoVoo = LocalDate.of(2025, 8, 21);
        assertThat(repositorioDeNovosVoos
                .findByOrigemAeroportoAndOrigemPartidaBetweenAndDestinoAeroporto(
                        Aeroporto.CGH,
                        dataDoVoo.atStartOfDay(),
                        dataDoVoo.plusDays(1).atStartOfDay(),
                        Aeroporto.SDU
                )).isEmpty();
    }
}