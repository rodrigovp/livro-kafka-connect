package br.com.alura.mspesquisa.infra.kafka;

import br.com.alura.mspesquisa.dominio.IdentificadorCompanhiaAerea;
import br.com.alura.mspesquisa.dominio.RepositorioDeNovosVoos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.KafkaNull;

import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.vooCriadoEvent;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ReceptorDeVoosUnitTest {
	
    private ReceptorDeVoos receptorDeVoos;
    private RepositorioDeNovosVoos repositorio;
    
    @BeforeEach
    void setUp(){
        repositorio = mock(RepositorioDeNovosVoos.class);
        receptorDeVoos = new ReceptorDeVoos(repositorio);         
    }
    
    @Test
    void receberNovoVoo(){
        var vooCriado = vooCriadoEvent();
        receptorDeVoos.novoVoo(vooCriado);
        verify(repositorio).save(vooCriado.toVoo());
    }
                             
    @Test
    void cancelarVooCriadoAnteriormente(){
        String idCompanhiaAerea = "{\"id\":\"65136e3571e71b6bcc8b3021\"}";
        receptorDeVoos.vooCancelado(idCompanhiaAerea, KafkaNull.INSTANCE);
        verify(repositorio).deleteByIdCompanhiaAerea(IdentificadorCompanhiaAerea.fromJson(idCompanhiaAerea));
    }
}
