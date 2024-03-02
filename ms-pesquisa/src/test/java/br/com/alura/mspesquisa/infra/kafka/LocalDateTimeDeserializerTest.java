package br.com.alura.mspesquisa.infra.kafka;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LocalDateTimeDeserializerTest {

    private LocalDateTimeDeserializer deserializer;

    @BeforeEach
    void setUp(){
        deserializer = new LocalDateTimeDeserializer();
    }

    @Test
    void deserializarUmaData() throws IOException {
        long dataLong = 1723671519000L;
        var jsonParser = mock(JsonParser.class);
        when(jsonParser.getValueAsLong()).thenReturn(dataLong);

        var dataEsperada = LocalDateTime.of(2024, 8, 14, 18, 38, 39);
        var dataObtida = deserializer.deserialize(jsonParser, mock(DeserializationContext.class));

        assertThat(dataObtida).isEqualTo(dataEsperada);
    }
}