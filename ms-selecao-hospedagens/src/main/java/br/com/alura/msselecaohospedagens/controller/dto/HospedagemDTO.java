package br.com.alura.msselecaohospedagens.controller.dto;

import br.com.alura.msselecaohospedagens.dominio.Aeroporto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class HospedagemDTO {
	
	@JsonProperty
	private String cnpj;
	
	@JsonProperty
	private String nome;
	
	@JsonProperty
	private String tipo;
	
	@JsonProperty
	private Aeroporto aeroportoMaisProximo;
	
	@JsonProperty
	private BigDecimal diariaAdulto;
	
	@JsonProperty
	private BigDecimal diariaCrianca;
}
