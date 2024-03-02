package br.com.alura.msmarketing.dominio;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.alura.msmarketing.dominio.ObjetosParaTestes.hospedagemSelecionadaComValores;
import static br.com.alura.msmarketing.dominio.ObjetosParaTestes.vooSelecionadoComValores;
import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.assertThat;

class PacoteTest {

    @Test
    void calcularValorTotal(){
        var vooSelecionado = vooSelecionadoComValores(TEN);
        var hospedagemSelecionada = hospedagemSelecionadaComValores(ONE, ZERO);

        var pacote = new Pacote(vooSelecionado, hospedagemSelecionada);

        assertThat(pacote.total()).isEqualTo(new BigDecimal("11"));
    }
}