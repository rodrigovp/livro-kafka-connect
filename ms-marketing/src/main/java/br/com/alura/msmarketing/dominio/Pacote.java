package br.com.alura.msmarketing.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record Pacote(VooSelecionado voo, HospedagemSelecionada hospedagem) {

	@JsonProperty
	public BigDecimal total() {
		return voo.total().add(hospedagem.total());
	}
	
	public String cnpjHospedagem() {
		return hospedagem.cnpj();
	}

	public String cpfComprador() {
		return voo.cpf();
	}
}
