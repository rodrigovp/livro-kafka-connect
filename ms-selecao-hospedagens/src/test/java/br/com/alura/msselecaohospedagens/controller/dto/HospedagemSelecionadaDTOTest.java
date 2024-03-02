package br.com.alura.msselecaohospedagens.controller.dto;

import br.com.alura.msselecaohospedagens.dominio.Aeroporto;
import br.com.alura.msselecaohospedagens.dominio.CpfComprador;
import br.com.alura.msselecaohospedagens.dominio.IdentificadorCompanhiaAerea;
import br.com.alura.msselecaohospedagens.dominio.RepositorioDeHospedagens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static br.com.alura.msselecaohospedagens.infra.ObjetosParaTestes.hospedagemSelecionadaDto;
import static br.com.alura.msselecaohospedagens.infra.ObjetosParaTestes.umaHospedagem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HospedagemSelecionadaDTOTest {

	private RepositorioDeHospedagens repositorio;
	
	@BeforeEach
	void setUp() {
		repositorio = mock(RepositorioDeHospedagens.class);
	}
	
	@Test
	void converterParaHospedagemSelecionada() {
		var cnpj = "32828204000101";
		var idCompanhiaAereaString = "SDFh3cjhcd";
		var cpfCompradorString = "80579633101";
		
		var hospedagem = umaHospedagem(cnpj, Aeroporto.BSB);
		var idCompanhiaAerea = new IdentificadorCompanhiaAerea(idCompanhiaAereaString);
		var cpfComprador = new CpfComprador(cpfCompradorString);
		
		when(repositorio.findByCnpj(cnpj)).thenReturn(Optional.of(hospedagem));
		
		var dto = hospedagemSelecionadaDto(cnpj, idCompanhiaAereaString, cpfCompradorString);
		
		var hospedagemSelecionadaObtida = dto.toHospedagemSelecionada(repositorio);
		var hospedagemSelecionadaEsperada = hospedagem.selecionar(idCompanhiaAerea, cpfComprador);
		
		assertThat(hospedagemSelecionadaObtida).isEqualTo(hospedagemSelecionadaEsperada);
	}
	
	@Test
	void lancarExcecaoSeCnpjHospedagemNaoExistir() {
		var cnpj = "32828204000101";
		var idCompanhiaAereaString = "SDFh3cjhcd";
		var cpfCompradorString = "80579633101";
		
		var exception = new IllegalArgumentException();
		when(repositorio.findByCnpj(cnpj)).thenThrow(exception);
		
		var dto = hospedagemSelecionadaDto(cnpj, idCompanhiaAereaString, cpfCompradorString);
		
		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> dto.toHospedagemSelecionada(repositorio));
	}

	@Test
	void lancarExcecaoSeCpfForInvalido() {
		var cnpj = "32828204000101";
		var idCompanhiaAereaString = "SDFh3cjhcd";
		var cpfCompradorString = "cpfinvalido";

		when(repositorio.findByCnpj(cnpj)).thenReturn(Optional.of(umaHospedagem(cnpj, Aeroporto.BSB)));

		var dto = hospedagemSelecionadaDto(cnpj, idCompanhiaAereaString, cpfCompradorString);

		assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(() -> dto.toHospedagemSelecionada(repositorio));
	}
}
