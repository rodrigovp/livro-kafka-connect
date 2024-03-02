package br.com.alura.mspesquisa.infra.kafka.event;

import br.com.alura.mspesquisa.dominio.Aeroporto;
import br.com.alura.mspesquisa.dominio.Origem;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.assertj.core.api.Assertions.assertThat;

class OrigemEventTest {

    @Test
    void converterParaOrigem() {
        var partida = LocalDateTime.parse("2024-08-14T18:38:39", ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        var aeroporto = Aeroporto.GRU.name();

        var origemEsperada = new Origem(Aeroporto.GRU, partida);
        var origemObtida = new OrigemEvent(aeroporto, partida).toOrigem();

        assertThat(origemObtida).isEqualTo(origemEsperada);
    }
}