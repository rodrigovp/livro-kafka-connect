package br.com.alura.mspesquisa.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
public class VooDto {

    @JsonProperty
    private String idCompanhiaAereaIdentificador;

    @JsonProperty
    private String origemAeroporto;

    @JsonProperty
    private LocalDateTime origemPartida;

    @JsonProperty
    private String destinoAeroporto;
    @JsonProperty
    private LocalDateTime destinoChegada;

    @JsonProperty
    private String companhiaAerea;

    @JsonProperty
    private BigDecimal precoAdulto;

    @JsonProperty
    private BigDecimal precoCrianca;
}
