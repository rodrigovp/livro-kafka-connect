package br.com.alura.msselecaohospedagens.dominio;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CpfCompradorTest {

    @Test
    void cpfValido() {
        assertDoesNotThrow(() -> new CpfComprador("81234021200"));
    }

    @Test
    void cpfInvalido() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new CpfComprador("65376587677"));
    }
}