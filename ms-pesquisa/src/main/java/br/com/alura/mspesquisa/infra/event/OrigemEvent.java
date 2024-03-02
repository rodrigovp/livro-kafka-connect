package br.com.alura.mspesquisa.infra.event;

import br.com.alura.mspesquisa.infra.LocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

record OrigemEvent(String aeroporto,
                   @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                 LocalDateTime partida) {
}
