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
public class RepositorioDeHospedagensSelecionadasTest {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private RepositorioDeHospedagensSelecionadas repositorioDeHospedagensSelecionadas;
    
    @Autowired
    private RepositorioDeHospedagens repositorioDeHospedagens;
    
    private Hospedagem umaHospedagem;
    private HospedagemSelecionada hospedagemSelecionada;
    
    @BeforeEach
    void setUp() {
    	umaHospedagem = umaHospedagem("68802182000158", Aeroporto.BSB);
		hospedagemSelecionada = umaHospedagem.selecionar(new IdentificadorCompanhiaAerea("identificador"), 
				new CpfComprador("48460072940"));
        
		transactionTemplate.executeWithoutResult(status -> {
        	repositorioDeHospedagens.save(umaHospedagem);
        });
		
		transactionTemplate.executeWithoutResult(status -> {
        	repositorioDeHospedagensSelecionadas.save(hospedagemSelecionada);
        });
    }
    
    @AfterEach
    void tearDown(){
    	transactionTemplate.executeWithoutResult(status -> {
            repositorioDeHospedagensSelecionadas.delete(hospedagemSelecionada);
        });
    	
    	transactionTemplate.executeWithoutResult(status -> {
            repositorioDeHospedagens.delete(umaHospedagem);
        });
    }
    
    @Test
    void persistir(){
        assertThat(repositorioDeHospedagensSelecionadas.findAll()).contains(hospedagemSelecionada);
    }
}
