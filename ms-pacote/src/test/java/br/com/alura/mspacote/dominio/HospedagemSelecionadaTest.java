package br.com.alura.mspacote.dominio;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.alura.mspacote.dominio.ObjetosParaTestes.hospedagemSelecionadaComValores;
import static org.assertj.core.api.Assertions.assertThat;

class HospedagemSelecionadaTest {

	@Test
	void calcularValorTotal() {
		assertThat(hospedagemSelecionadaComValores(BigDecimal.ONE, BigDecimal.ONE)
				.total()).isEqualTo(BigDecimal.valueOf(2));
	}

}
