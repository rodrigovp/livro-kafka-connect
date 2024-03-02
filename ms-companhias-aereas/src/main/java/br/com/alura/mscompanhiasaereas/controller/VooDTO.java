package br.com.alura.mscompanhiasaereas.controller;

import br.com.alura.mscompanhiasaereas.dominio.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
public class VooDTO {

    @JsonProperty
    private String origem;
    @JsonProperty
    private String partida;
    @JsonProperty
    private String destino;
    @JsonProperty
    private String chegada;
    @JsonProperty
    private String companhiaAerea;

    @JsonProperty
    private BigDecimal precoAdulto;

    @JsonProperty
    private BigDecimal precoCrianca;

    public Voo toVoo() {
        return new Voo(new Origem(Aeroporto.valueOf(origem), LocalDateTime.parse(partida)),
                new Destino(Aeroporto.valueOf(destino), LocalDateTime.parse(chegada)),
                CompanhiaAerea.valueOf(companhiaAerea), new Preco(precoAdulto, precoCrianca));
    }
}