package br.com.alura.msmarketing.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record Hospedagem (
		Long id,
        
        @JsonProperty("aeroporto_mais_proximo")
        String aeroportoMaisProximo,
        String cnpj,

        @JsonProperty("valor_adulto")
        BigDecimal valorAdulto,

        @JsonProperty("valor_crianca")
        BigDecimal valorCrianca,
        String nome,
        String tipo
        ) {
}
