package br.com.alura.msmarketing.infra.kafka;

import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.common.serialization.Serdes.String;

@Configuration
class KafkaObjects {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServerUrl;

    @Value("${application.id.config}")
    private String idDaAplicacao;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration getStreamsConfiguration() {
        Map<String, Object> streamsConfiguration = new HashMap<>();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, idDaAplicacao);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "1");

        return new KafkaStreamsConfiguration(streamsConfiguration);
    }
}
