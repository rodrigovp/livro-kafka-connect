package br.com.alura.mspacote.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record Pacote(VooSelecionado voo, HospedagemSelecionada hospedagem) {

	@JsonProperty
	public BigDecimal total() {
		return voo.total().add(hospedagem.total());
	}
}
