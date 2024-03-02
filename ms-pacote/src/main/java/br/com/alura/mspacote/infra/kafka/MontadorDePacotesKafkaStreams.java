package br.com.alura.mspacote.infra.kafka;

import br.com.alura.mspacote.dominio.HospedagemSelecionada;
import br.com.alura.mspacote.dominio.Pacote;
import br.com.alura.mspacote.dominio.VooSelecionado;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;

import static org.apache.kafka.common.serialization.Serdes.String;
import static org.apache.kafka.common.serialization.Serdes.serdeFrom;
import static org.apache.kafka.streams.kstream.Consumed.with;

@Configuration
@EnableKafkaStreams
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
    
//    @Bean
//    KafkaStreams kafkaStreams(StreamsBuilder builder){
//        KafkaStreams kafkaStreams = new KafkaStreams(getStream(builder).build(), getStreamsConfiguration());
//
//        //kafkaStreams.cleanUp();
//        
//        kafkaStreams.start();
//        
//        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
//
//        return kafkaStreams;
//    }
    
    @Autowired
    void getStream(StreamsBuilder builder) {
        //StreamsBuilder builder = new StreamsBuilder();

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
    }

//    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
//    KafkaStreamsConfiguration getStreamsConfiguration() {
//        Map<String, Object> streamsConfiguration = new HashMap<>();
//        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, idDaAplicacao);
//        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);
//        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, String().getClass().getName());
//        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "1");
//
//        return new KafkaStreamsConfiguration(streamsConfiguration);
//    }
}
