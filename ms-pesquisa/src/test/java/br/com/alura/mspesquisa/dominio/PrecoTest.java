package br.com.alura.mspesquisa.dominio;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PrecoTest {

    private Preco preco = new Preco(new BigDecimal("500"), new BigDecimal("200.1"));

    @Test
    void calcularPrecos(){
        assertThat(preco.paraAdultos(2)).isEqualTo(new BigDecimal("1000.00"));
        assertThat(preco.paraCriancas(2)).isEqualTo(new BigDecimal("400.20"));
    }
}