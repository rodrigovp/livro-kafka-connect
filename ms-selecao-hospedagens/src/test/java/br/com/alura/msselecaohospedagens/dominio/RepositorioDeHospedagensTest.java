package br.com.alura.msselecaohospedagens.dominio;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;

import static br.com.alura.msselecaohospedagens.infra.ObjetosParaTestes.umaHospedagem;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RepositorioDeHospedagensTest {

	@Autowired
	private RepositorioDeHospedagens repositorioDeHospedagens;
	
	@Autowired
	private TransactionTemplate transactionTemplate;

	private Aeroporto aeroporto;
	private String cnpj;
	private Hospedagem hospedagem;
	
	@BeforeEach
	void setUp() {
		aeroporto = Aeroporto.CGH;
		cnpj = "57559130000186";
        hospedagem = umaHospedagem(cnpj, aeroporto);
        
        transactionTemplate.executeWithoutResult(status -> {
            repositorioDeHospedagens.save(hospedagem);
        });
	}
	
	@AfterEach
	void tearDown() {
        transactionTemplate.executeWithoutResult(status -> {
            repositorioDeHospedagens.delete(hospedagem);
        });
	}
	
	@Test
	void buscarEEncontrarHospedagensPorAeroporto() {
		assertThat(repositorioDeHospedagens
				.findByAeroportoMaisProximo(aeroporto)).contains(hospedagem);
	}
	
	@Test
	void buscarENaoEncontrarHospedagensPorAeroporto() {
		assertThat(repositorioDeHospedagens
				.findByAeroportoMaisProximo(Aeroporto.GRU)).doesNotContain(hospedagem);
	}
	
	@Test
	void buscarEEncontrarPorCnpj() {
		assertThat(repositorioDeHospedagens.findByCnpj(cnpj)).contains(hospedagem);
	}
	
	@Test
	void buscarENaoEncontrarPorCnpj() {
		assertThat(repositorioDeHospedagens.findByCnpj("inexistente")).isEmpty();
	}
}
