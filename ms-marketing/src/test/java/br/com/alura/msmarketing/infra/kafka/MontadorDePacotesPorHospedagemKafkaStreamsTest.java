package br.com.alura.msmarketing.infra.kafka;

import br.com.alura.msmarketing.dominio.Hospedagem;
import br.com.alura.msmarketing.dominio.Pacote;
import br.com.alura.msmarketing.dominio.PacotesPorHospedagem;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.awaitility.core.ThrowingRunnable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static br.com.alura.msmarketing.dominio.ObjetosParaTestes.hospedagemComCnpj;
import static br.com.alura.msmarketing.dominio.ObjetosParaTestes.pacoteParaHospedagemComCnpj;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@TestPropertySource(properties = "spring.kafka.streams.auto-startup=false")
@EmbeddedKafka(topics = {
        "${topico.pacotes_criados.nome}",
        "${topico.hospedagens.nome}",
        "${topico.pacotes_por_hospedagem.nome}"
})
class MontadorDePacotesPorHospedagemKafkaStreamsTest {

    @Value("${topico.pacotes_criados.nome}")
    private String topicoDePacotesCriados;

    @Value("${topico.hospedagens.nome}")
    private String topicoDeHospedagens;

    @Value("${topico.pacotes_por_hospedagem.nome}")
    private String topicoDePacotesPorHospedagem;

    @Autowired
    private KafkaTemplate<String, Pacote> pacoteKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Hospedagem> hospedagemKafkaTemplate;

    @Autowired
    private KafkaConsumer<String, PacotesPorHospedagem> pacotesPorHospedagemKafkaConsumer;

    @Autowired
    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    private Pacote pacote;
    private Hospedagem hospedagem;

    @BeforeEach
    void setUp() {
        var cnpj = "78654566000176";
        pacote = pacoteParaHospedagemComCnpj(cnpj);
        hospedagem = hospedagemComCnpj(cnpj);

        streamsBuilderFactoryBean.start();
    }

    @AfterEach
    void destroy() {
        streamsBuilderFactoryBean.getKafkaStreams().close();
        streamsBuilderFactoryBean.getKafkaStreams().cleanUp();
    }

    @Test
    void aoEnviarUmPacoteDeveSerCriadoUmPacotePorHospedagem() {
        var pacotesPorHospedagem = new PacotesPorHospedagem(hospedagem, new BigDecimal("12"));

        pacoteKafkaTemplate.send(topicoDePacotesCriados, pacote.cpfComprador(), pacote);
        hospedagemKafkaTemplate.send(topicoDeHospedagens, hospedagem.cnpj(), hospedagem);

        esperarUmTempoAte(() -> assertThat(mensagensDoTopico(pacotesPorHospedagemKafkaConsumer)).contains(pacotesPorHospedagem));
    }

    @Test
    void aoEnviarDoisPacotesDeUmaMesmaHospedagemOsValoresDevemSerSomados(){
        var pacotesPorHospedagem = new PacotesPorHospedagem(hospedagem, new BigDecimal("24"));

        pacoteKafkaTemplate.send(topicoDePacotesCriados, pacote.cpfComprador(), pacote);
        pacoteKafkaTemplate.send(topicoDePacotesCriados, pacote.cpfComprador(), pacote);
        hospedagemKafkaTemplate.send(topicoDeHospedagens, hospedagem.cnpj(), hospedagem);

        esperarUmTempoAte(() -> assertThat(mensagensDoTopico(pacotesPorHospedagemKafkaConsumer)).contains(pacotesPorHospedagem));
    }

    private List<PacotesPorHospedagem> mensagensDoTopico(KafkaConsumer<String, PacotesPorHospedagem> consumidor){
        List<PacotesPorHospedagem> eventos = new ArrayList<>();
        consumidor.poll(Duration.ofMillis(1000)).forEach(consumerRecord ->
                eventos.add(consumerRecord.value())
        );
        return eventos;
    }

    private void esperarUmTempoAte(ThrowingRunnable runnable){
        await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(runnable);
    }
}