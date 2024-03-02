package br.com.alura.mspacote.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record HospedagemSelecionada(
	String cnpj,

	@JsonProperty("cpf_comprador")
	String cpfComprador,
		
	@JsonProperty("valor_adulto")
	BigDecimal valorAdulto,
		
	@JsonProperty("valor_crianca")
	BigDecimal valorCrianca,
	String identificador,
	String nome
	) {

	public BigDecimal total() {
		return valorAdulto.add(valorCrianca);
	}
}