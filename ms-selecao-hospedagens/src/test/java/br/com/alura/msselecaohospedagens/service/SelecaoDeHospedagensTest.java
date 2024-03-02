package br.com.alura.msselecaohospedagens.service;

import br.com.alura.msselecaohospedagens.dominio.Aeroporto;
import br.com.alura.msselecaohospedagens.dominio.Hospedagem;
import br.com.alura.msselecaohospedagens.dominio.RepositorioDeHospedagens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static br.com.alura.msselecaohospedagens.infra.ObjetosParaTestes.umaHospedagem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SelecaoDeHospedagensTest {

	private RepositorioDeHospedagens repositorioDeHospedagens;
	
	private SelecaoDeHospedagens selecaoDeHospedagens;
	
	private Aeroporto aeroporto;
	private Hospedagem hospedagem;
	
	@BeforeEach
	void setUp() {
		repositorioDeHospedagens = mock(RepositorioDeHospedagens.class);
		selecaoDeHospedagens = new SelecaoDeHospedagens(repositorioDeHospedagens);
		
		aeroporto = Aeroporto.CGH;
        hospedagem = umaHospedagem("68802182000158", aeroporto);
        
        when(repositorioDeHospedagens
				.findByAeroportoMaisProximo(aeroporto))
		.thenReturn(Set.of(hospedagem));
	}
	
	@Test
	void buscarEEncontrarHospedagens() {
		assertThat(selecaoDeHospedagens.buscarPor(aeroporto)).contains(hospedagem);
	}
	
	@Test
	void buscarENaoEncontrarHospedagens() {
		assertThat(selecaoDeHospedagens.buscarPor(Aeroporto.BSB)).isEmpty();
	}
}
