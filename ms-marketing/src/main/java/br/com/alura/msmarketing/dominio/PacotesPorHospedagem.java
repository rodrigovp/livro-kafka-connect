package br.com.alura.msmarketing.dominio;

import java.math.BigDecimal;

public record PacotesPorHospedagem(
	
	Hospedagem hospedagem,
	BigDecimal valorTotal
	) {

	public String cnpjHospedagem() {
		return hospedagem.cnpj();
	}
}
