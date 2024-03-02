package br.com.alura.mspesquisa.infra.kafka.event;

import br.com.alura.mspesquisa.dominio.Aeroporto;
import br.com.alura.mspesquisa.dominio.Origem;
import br.com.alura.mspesquisa.infra.kafka.LocalDateTimeDeserializer;
import br.com.alura.mspesquisa.infra.kafka.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public record OrigemEvent(String aeroporto,
                          @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                                  @JsonSerialize(using = LocalDateTimeSerializer.class)
                          LocalDateTime partida) {
    public Origem toOrigem() {
        return new Origem(Aeroporto.valueOf(aeroporto), partida);
    }
}
