package br.com.alura.mspesquisa.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.cpf;
import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.umVoo;
import static org.assertj.core.api.Assertions.assertThat;

class VooSelecionadoTest {

    private IdentificadorCompanhiaAerea idCompanhiaAerea;
    private Voo voo;

    private VooSelecionado vooSelecionado;

    @BeforeEach
    void setUp(){
        idCompanhiaAerea = new IdentificadorCompanhiaAerea("identificador");
        voo = umVoo(idCompanhiaAerea);
    }

    @Test
    void lerIdCompanhiaAerea(){
        var vooSelecionado = new VooSelecionado(voo, 1,1, cpf());

        assertThat(vooSelecionado.idCompanhiaAerea()).isEqualTo(idCompanhiaAerea);
    }

    @Test
    void valoresTotais(){
        var vooSelecionado = new VooSelecionado(voo, 4, 0, cpf());

        assertThat(vooSelecionado).extracting("totalAdultos").isEqualTo(new BigDecimal("4000.00"));
        assertThat(vooSelecionado).extracting("totalCriancas").isEqualTo(new BigDecimal("0.00"));
        assertThat(vooSelecionado).extracting("total").isEqualTo(new BigDecimal("4000.00"));
    }
}