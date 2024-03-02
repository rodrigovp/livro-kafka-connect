package br.com.alura.mspesquisa.infra.kafka.event;

import br.com.alura.mspesquisa.dominio.Preco;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PrecoEventTest {

    @Test
    void converterParaPreco() {
        var precoAdulto = new BigDecimal("1000.0");
        var precoCrianca = new BigDecimal("500.0");

        var precoEsperado = new Preco(precoAdulto.setScale(2), precoCrianca.setScale(2));
        var precoObtido = new PrecoEvent(precoAdulto, precoCrianca).toPreco();

        assertThat(precoObtido).isEqualTo(precoEsperado);
    }
}