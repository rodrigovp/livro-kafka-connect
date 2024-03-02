package br.com.alura.mspesquisa.infra.kafka.event;

import br.com.alura.mspesquisa.dominio.Aeroporto;
import br.com.alura.mspesquisa.dominio.Destino;
import br.com.alura.mspesquisa.infra.kafka.LocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

public record DestinoEvent(String aeroporto,
                           @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                  LocalDateTime chegada) {
    public Destino toDestino() {
        return new Destino(Aeroporto.valueOf(aeroporto), chegada);
    }
}
