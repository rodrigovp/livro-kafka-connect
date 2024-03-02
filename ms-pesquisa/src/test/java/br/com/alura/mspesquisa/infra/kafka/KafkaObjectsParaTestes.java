package br.com.alura.mspesquisa.infra.kafka;

import br.com.alura.mspesquisa.dominio.IdentificadorCompanhiaAerea;
import br.com.alura.mspesquisa.infra.kafka.event.VooCriadoEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
class KafkaObjectsParaTestes {

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServerUrl;

	@Bean
	KafkaTemplate<String, VooCriadoEvent> vooCriadoKafkaTemplate() {
		return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(produtorConfigs()));
	}

    @Bean
    KafkaTemplate<IdentificadorCompanhiaAerea, KafkaNull> vooCanceladoKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(produtorConfigs()));
    }

	private Map<String, Object> produtorConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return props;
	}
}
