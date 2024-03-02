package br.com.alura.mspacote.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record VooSelecionado(
	int adultos,
	String cpf,
	int criancas,
	String identificador,
	String destino,
	BigDecimal total,
		
	@JsonProperty("total_adultos")
	BigDecimal totalAdultos,
		
	@JsonProperty("total_criancas")
	BigDecimal totalCriancas) {
}
