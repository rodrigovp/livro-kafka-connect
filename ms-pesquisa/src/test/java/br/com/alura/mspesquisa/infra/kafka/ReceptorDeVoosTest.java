package br.com.alura.mspesquisa.infra.kafka;

import br.com.alura.mspesquisa.dominio.IdentificadorCompanhiaAerea;
import br.com.alura.mspesquisa.dominio.RepositorioDeNovosVoos;
import br.com.alura.mspesquisa.infra.kafka.event.VooCriadoEvent;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest

// ao comentar a linha abaixo, o teste passará a acessar o Kafka real.
@EmbeddedKafka(bootstrapServersProperty = "spring.embedded.kafka.brokers")
class ReceptorDeVoosTest {

	@SpyBean
	private ReceptorDeVoos receptor;
	
	@MockBean
	private RepositorioDeNovosVoos repositorioDeNovosVoos;
	
	@Captor
	private ArgumentCaptor<VooCriadoEvent> vooCriadoEventCaptor;
	
	@Autowired
	private KafkaTemplate<String, VooCriadoEvent> vooCriadoKafkaTemplate;
	
	@Autowired
	private KafkaTemplate<IdentificadorCompanhiaAerea, KafkaNull> vooCanceladoKafkaTemplate;

    @Value("${topico.voos.nome}")
    private String topicoNovosVoos;

    @Test
    void receberNovoVoo() {
        vooCriadoKafkaTemplate.send(topicoNovosVoos, vooCriadoEvent());

        verify(receptor, timeout(5000).times(1)).novoVoo(vooCriadoEventCaptor.capture());
		assertThat(vooCriadoEventCaptor.getValue()).isEqualTo(vooCriadoEvent());
        verify(repositorioDeNovosVoos, timeout(5000).times(1)).save(vooCriadoEventCaptor.getValue().toVoo());
    }
    
    @Test
    void cancelarVooCriadoAnteriormente() {
    	var identificadorCompanhiaAerea = identificadorCompanhiaAerea();
    	
    	vooCanceladoKafkaTemplate.send(topicoNovosVoos, identificadorCompanhiaAerea, null);
    	
    	verify(receptor, timeout(5000).times(1)).vooCancelado(toJson(identificadorCompanhiaAerea), null);
    	verify(repositorioDeNovosVoos, timeout(5000).times(1)).deleteByIdCompanhiaAerea(identificadorCompanhiaAerea);
    }
}
