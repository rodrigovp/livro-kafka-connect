package br.com.alura.mspesquisa.infra.kafka.event;

import br.com.alura.mspesquisa.dominio.Destino;
import br.com.alura.mspesquisa.dominio.IdentificadorCompanhiaAerea;
import br.com.alura.mspesquisa.dominio.Origem;
import br.com.alura.mspesquisa.dominio.Preco;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.alura.mspesquisa.dominio.Aeroporto.CGH;
import static br.com.alura.mspesquisa.dominio.Aeroporto.SDU;
import static br.com.alura.mspesquisa.dominio.CompanhiaAerea.GOL;
import static br.com.alura.mspesquisa.dominio.DateUtils.localDateTimeOf;
import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.vooCriadoEvent;
import static org.assertj.core.api.Assertions.assertThat;

class VooCriadoEventTest {

    @Test
    void converterParaVoo() {
        var vooCriadoEvent = vooCriadoEvent();
        var vooObtido = vooCriadoEvent.toVoo();

        assertThat(vooObtido)
                .hasFieldOrPropertyWithValue("idCompanhiaAerea", new IdentificadorCompanhiaAerea("65136e3571e71b6bcc8b3021"))
                .hasFieldOrPropertyWithValue("origem", new Origem(CGH, localDateTimeOf("2024-08-14T18:38:39")))
                .hasFieldOrPropertyWithValue("destino", new Destino(SDU, localDateTimeOf("2024-08-14T20:38:39")))
                .hasFieldOrPropertyWithValue("companhiaAerea", GOL)
                .hasFieldOrPropertyWithValue("preco", new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00")));
    }

    @Test
    void converterParaJson() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();

        var vooCriadoEventJson = objectMapper.writeValueAsString(vooCriadoEvent());
        var vooCriadoEventObtido = objectMapper.readValue(vooCriadoEventJson, VooCriadoEvent.class);

        assertThat(vooCriadoEventObtido).isEqualTo(vooCriadoEvent());
    }
}