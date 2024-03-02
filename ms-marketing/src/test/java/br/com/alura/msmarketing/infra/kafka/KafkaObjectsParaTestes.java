package br.com.alura.msmarketing.infra.kafka;

import br.com.alura.msmarketing.dominio.Hospedagem;
import br.com.alura.msmarketing.dominio.Pacote;
import br.com.alura.msmarketing.dominio.PacotesPorHospedagem;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;

@Configuration
class KafkaObjectsParaTestes {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServerUrl;

    @Value("${spring.kafka.group_id}")
    private String groupId;

    @Value("${spring.kafka.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${topico.pacotes_por_hospedagem.nome}")
    private String topicoDePacotesCriados;

    @Bean
    KafkaTemplate<String, Pacote> pacoteKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(produtorConfigs()));
    }

    @Bean
    KafkaTemplate<String, Hospedagem> hospedagemKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(produtorConfigs()));
    }

    @Bean
    KafkaConsumer<String, PacotesPorHospedagem> pacotesPorHospedagemKafkaConsumer(){
        KafkaConsumer<String, PacotesPorHospedagem> consumidor =
                new KafkaConsumer<>(consumerConfigs(PacotesPorHospedagem.class));
        consumidor.subscribe(singletonList(topicoDePacotesCriados));
        return consumidor;
    }

    private Map<String, Object> produtorConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

    private Map<String, Object> consumerConfigs(Class<?> ...clazz) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-para-consumidor-de-teste");
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
