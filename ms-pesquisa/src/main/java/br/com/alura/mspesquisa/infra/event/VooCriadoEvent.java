package br.com.alura.mspesquisa.infra.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class VooCriadoEvent {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("origem")
    private OrigemEvent origem;

    @JsonProperty("destino")
    private DestinoEvent destino;

    @JsonProperty("companhiaAerea")
    private String companhiaAerea;

    @JsonProperty("preco")
    private PrecoEvent preco;
}
