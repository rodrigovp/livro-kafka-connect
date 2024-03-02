package br.com.alura.mspesquisa.infra.kafka;

import br.com.alura.mspesquisa.dominio.IdentificadorCompanhiaAerea;
import br.com.alura.mspesquisa.dominio.RepositorioDeNovosVoos;
import br.com.alura.mspesquisa.infra.kafka.event.VooCriadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${topico.voos.nome}",
        groupId = "${spring.kafka.group_id}",
        containerFactory = "voosKafkaListener")
class ReceptorDeVoos {

    private RepositorioDeNovosVoos repositorioDeNovosVoos;

    @Autowired
    ReceptorDeVoos(RepositorioDeNovosVoos repositorioDeNovosVoos) {
        this.repositorioDeNovosVoos = repositorioDeNovosVoos;
    }

    @KafkaHandler
    void novoVoo(VooCriadoEvent vooCriadoEvent) {
        System.out.println("Voo recebido: " + vooCriadoEvent);
        repositorioDeNovosVoos.save(vooCriadoEvent.toVoo());
    }

    @KafkaHandler
    void vooCancelado(@Header(KafkaHeaders.RECEIVED_KEY) String idVoo,
                      @Payload(required = false) KafkaNull valorNulo) {
        var identificadorCompanhiaAerea = IdentificadorCompanhiaAerea.fromJson(idVoo);
        System.out.println("Voo cancelado: " + identificadorCompanhiaAerea);
        repositorioDeNovosVoos.deleteByIdCompanhiaAerea(identificadorCompanhiaAerea);
    }
}
