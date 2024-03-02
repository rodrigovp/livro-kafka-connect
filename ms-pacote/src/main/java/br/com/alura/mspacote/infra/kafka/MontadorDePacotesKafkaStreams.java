package br.com.alura.mspacote.infra.kafka;

import br.com.alura.mspacote.dominio.HospedagemSelecionada;
import br.com.alura.mspacote.dominio.Pacote;
import br.com.alura.mspacote.dominio.VooSelecionado;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.util.Properties;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.common.serialization.Serdes.String;
import static org.apache.kafka.common.serialization.Serdes.serdeFrom;
import static org.apache.kafka.streams.StreamsConfig.*;
import static org.apache.kafka.streams.kstream.Consumed.with;

@Configuration
class MontadorDePacotesKafkaStreams {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServerUrl;

    @Value("${topico.voos_selecionados.nome}")
    private String topicoDeVoosSelecionados;

    @Value("${topico.hospedagens_selecionadas.nome}")
    private String topicoDeHospedagensSelecionadas;
    
    @Value("${topico.pacotes_criados.nome}")
    private String topicoDePacotesCriados;

    @Value("${application.id.config}")
    private String idDaAplicacao;
    
    @Bean
    KafkaStreams kafkaStreams(){
        KafkaStreams kafkaStreams = new KafkaStreams(getStream().build(), getStreamsConfiguration());

        kafkaStreams.cleanUp();
        
        kafkaStreams.start();
        
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

        return kafkaStreams;
    }
    
    private StreamsBuilder getStream() {
        StreamsBuilder builder = new StreamsBuilder();

        Serde<VooSelecionado> serdeVooSelecionadoEvent =
                serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(VooSelecionado.class));
        Serde<HospedagemSelecionada> serdeHospedagemSelecionadaEvent =
                serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(HospedagemSelecionada.class));
        Serde<Pacote> serdePacoteEvent =
                serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(Pacote.class));

        KStream<String, VooSelecionado> streamVooSelecionadoEvent =
                builder.stream(topicoDeVoosSelecionados, with(String(), serdeVooSelecionadoEvent));

        KStream<String, HospedagemSelecionada> streamHospedagemSelecionadaEvent =
                builder.stream(topicoDeHospedagensSelecionadas, with(String(), serdeHospedagemSelecionadaEvent));

        streamVooSelecionadoEvent
                .join(streamHospedagemSelecionadaEvent,
                        (vooSelecionado, hospedagemSelecionada) -> new Pacote(vooSelecionado, hospedagemSelecionada),
                        JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofSeconds(5)),
                        StreamJoined.with(String(), serdeVooSelecionadoEvent, serdeHospedagemSelecionadaEvent)
                )
                .to(topicoDePacotesCriados, Produced.with(String(), serdePacoteEvent));

        return builder;
    }

    private Properties getStreamsConfiguration() {
        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(APPLICATION_ID_CONFIG, idDaAplicacao);
        streamsConfiguration.put(BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);
        streamsConfiguration.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, String().getClass().getName());
        streamsConfiguration.put(COMMIT_INTERVAL_MS_CONFIG, "1");

        return streamsConfiguration;
    }
}
