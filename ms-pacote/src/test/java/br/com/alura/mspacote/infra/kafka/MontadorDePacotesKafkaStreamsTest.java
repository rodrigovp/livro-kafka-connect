package br.com.alura.mspacote.infra.kafka;

import br.com.alura.mspacote.dominio.HospedagemSelecionada;
import br.com.alura.mspacote.dominio.Pacote;
import br.com.alura.mspacote.dominio.VooSelecionado;
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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static br.com.alura.mspacote.dominio.ObjetosParaTestes.hospedagemSelecionadaComValores;
import static br.com.alura.mspacote.dominio.ObjetosParaTestes.vooSelecionadoComValores;
import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@TestPropertySource(properties = "spring.kafka.streams.auto-startup=false")
@EmbeddedKafka(topics = {
        "${topico.voos_selecionados.nome}",
        "${topico.hospedagens_selecionadas.nome}",
        "${topico.pacotes_criados.nome}"
})
class MontadorDePacotesKafkaStreamsTest {

    @Value("${topico.voos_selecionados.nome}")
    private String topicoDeVoosSelecionados;

    @Value("${topico.hospedagens_selecionadas.nome}")
    private String topicoDeHospedagensSelecionadas;

    @Value("${topico.pacotes_criados.nome}")
    private String topicoDePacotesCriados;

    @Autowired
    private KafkaTemplate<String, VooSelecionado> vooKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, HospedagemSelecionada> hospedagemKafkaTemplate;

    @Autowired
    private KafkaConsumer<String, Pacote> pacoteKafkaConsumer;

    @Autowired
    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    private VooSelecionado vooSelecionado;
    private HospedagemSelecionada hospedagemSelecionada;
    private Pacote pacote;

    @BeforeEach
    void setUp() {
        vooSelecionado = vooSelecionadoComValores(TEN);
        hospedagemSelecionada = hospedagemSelecionadaComValores(ONE, ZERO);
        pacote = new Pacote(vooSelecionado, hospedagemSelecionada);

        streamsBuilderFactoryBean.start();
    }

    @AfterEach
    void destroy() {
        streamsBuilderFactoryBean.getKafkaStreams().close();
        streamsBuilderFactoryBean.getKafkaStreams().cleanUp();
    }

    @Test
    void aoEnviarVooEHospedagemDeveCriarPacote() {
        vooKafkaTemplate.send(topicoDeVoosSelecionados, vooSelecionado.cpf(), vooSelecionado);
        hospedagemKafkaTemplate.send(topicoDeHospedagensSelecionadas, hospedagemSelecionada.cpfComprador(), hospedagemSelecionada);

        esperarUmTempoAte(() -> assertThat(mensagensDoTopico(pacoteKafkaConsumer)).contains(pacote));
    }

    @Test
    void aoEnviarHospedagemEVooDeveCriarPacote() {
        hospedagemKafkaTemplate.send(topicoDeHospedagensSelecionadas, hospedagemSelecionada.cpfComprador(), hospedagemSelecionada);
        vooKafkaTemplate.send(topicoDeVoosSelecionados, vooSelecionado.cpf(), vooSelecionado);

        esperarUmTempoAte(() -> assertThat(mensagensDoTopico(pacoteKafkaConsumer)).contains(pacote));
    }

    private List<Pacote> mensagensDoTopico(KafkaConsumer<String, Pacote> consumidor){
        List<Pacote> eventos = new ArrayList<>();
        consumidor.poll(Duration.ofMillis(1000)).forEach(consumerRecord ->
                eventos.add(consumerRecord.value())
        );

        return eventos;
    }

    private void esperarUmTempoAte(ThrowingRunnable runnable){
        await()
                .atMost(Duration.ofSeconds(6))
                .untilAsserted(runnable);
    }
}
