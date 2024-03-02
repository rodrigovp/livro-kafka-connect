package br.com.alura.msmarketing.infra.kafka;

import br.com.alura.msmarketing.dominio.Hospedagem;
import br.com.alura.msmarketing.dominio.Pacote;
import br.com.alura.msmarketing.dominio.PacotesPorHospedagem;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import static org.apache.kafka.common.serialization.Serdes.String;
import static org.apache.kafka.common.serialization.Serdes.serdeFrom;
import static org.apache.kafka.streams.kstream.Consumed.with;

@Configuration
@EnableKafkaStreams
class MontadorDePacotesPorHospedagemKafkaStreams {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServerUrl;

    @Value("${topico.pacotes_criados.nome}")
    private String topicoDePacotesCriados;

    @Value("${topico.hospedagens.nome}")
    private String topicoDeHospedagens;

    @Value("${topico.pacotes_por_hospedagem.nome}")
    private String topicoDePacotesPorHospedagem;

    @Value("${application.id.config}")
    private String idDaAplicacao;
    
//    @Bean
//    KafkaStreams kafkaStreams(){
//        KafkaStreams kafkaStreams = new KafkaStreams(getStream().build(), getStreamsConfiguration());
//
//        kafkaStreams.cleanUp();
//
//        kafkaStreams.start();
//
//        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
//
//        return kafkaStreams;
//    }

    @Autowired
    void getStream(StreamsBuilder builder){
        //StreamsBuilder builder = new StreamsBuilder();

        exibirLocalizacaoDoRocksDB();

        Serde<Hospedagem> serdeHospedagemEvent = serdeFrom(new JsonSerializer<>(), jsonDeserializer(Hospedagem.class));
        Serde<Pacote> serdePacoteEvent = serdeFrom(new JsonSerializer<>(), jsonDeserializer(Pacote.class));
        Serde<PacotesPorHospedagem> serdePacotesPorHospedagemEvent = serdeFrom(new JsonSerializer<>(), jsonDeserializer(PacotesPorHospedagem.class));

        KTable<String, Hospedagem> tableHospedagemEvent = builder.table(topicoDeHospedagens, with(String(), serdeHospedagemEvent));
        KTable<String, Pacote> tablePacoteEvent = builder.table(topicoDePacotesCriados, with(String(), serdePacoteEvent));
        
        tablePacoteEvent.join(
                        tableHospedagemEvent,
                        Pacote::cnpjHospedagem,
                        (pacote, hospedagem) -> new PacotesPorHospedagem(hospedagem, pacote.total()))
                .toStream()
                .selectKey((chaveAtual, pacote) -> pacote.cnpjHospedagem())
                .groupByKey(Grouped.with(String(), serdePacotesPorHospedagemEvent))
                .reduce((p1, p2) -> new PacotesPorHospedagem(p1.hospedagem(), p1.valorTotal().add(p2.valorTotal())))
                .toStream()
                .to(topicoDePacotesPorHospedagem, Produced.with(String(), serdePacotesPorHospedagemEvent));
    }

    private void exibirLocalizacaoDoRocksDB() {
        System.out.printf("Localização do RocksDB: %skafka-streams\n", System.getProperty("java.io.tmpdir"));
    }

    private <T> JsonDeserializer<T> jsonDeserializer(Class<T> clazz){
		return new JsonDeserializer<T>(clazz, false);
	}

//    private Properties getStreamsConfiguration() {
//        Properties streamsConfiguration = new Properties();
//        streamsConfiguration.put(APPLICATION_ID_CONFIG, idDaAplicacao);
//        streamsConfiguration.put(BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);
//        streamsConfiguration.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, String().getClass().getName());
//        streamsConfiguration.put(COMMIT_INTERVAL_MS_CONFIG, "1");
//
//        return streamsConfiguration;
//    }
}
