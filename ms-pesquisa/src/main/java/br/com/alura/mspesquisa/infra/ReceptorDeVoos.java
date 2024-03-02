package br.com.alura.mspesquisa.infra;

import br.com.alura.mspesquisa.infra.event.VooCriadoEvent;
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
public class ReceptorDeVoos {

    @KafkaHandler
    void novoVoo(VooCriadoEvent novoVoo) {
        System.out.println("Voo recebido: " + novoVoo);
    }

    @KafkaHandler
    void vooCancelado(@Header(KafkaHeaders.RECEIVED_KEY) String idVoo,
                      @Payload(required = false) KafkaNull valorNulo) {
        System.out.println("Voo cancelado: " + idVoo);
    }
}
