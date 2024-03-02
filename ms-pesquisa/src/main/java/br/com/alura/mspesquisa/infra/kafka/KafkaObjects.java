package br.com.alura.mspesquisa.infra.kafka;

import br.com.alura.mspesquisa.infra.kafka.event.VooCriadoEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

@Configuration
class KafkaObjects {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServerUrl;

    @Value("${spring.kafka.group_id}")
    private String groupId;

    @Value("${spring.kafka.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${topico.voos.nome}")
    private String nomeDoTopico;

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, VooCriadoEvent>> voosKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, VooCriadoEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<String, VooCriadoEvent>(
                consumerConfigs(VooCriadoEvent.class),
                new StringDeserializer(),
                jsonDeserializer(VooCriadoEvent.class)));
        return factory;
    }

    private <T> JsonDeserializer<T> jsonDeserializer(Class<T> clazz){
        return new JsonDeserializer<T>(clazz, false);
    }

    private Map<String, Object> consumerConfigs(Class<?> ... clazz) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TYPE_MAPPINGS, typeMappings(clazz));

        return props;
    }

    private String typeMappings(Class<?> ...classes) {
        return stream(classes)
                .map(clazz -> clazz.getSimpleName().toLowerCase() + ":" + clazz.getName()
                ).collect(joining(","));
    }
}
