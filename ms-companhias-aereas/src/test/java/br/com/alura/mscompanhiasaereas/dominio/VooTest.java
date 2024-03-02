package br.com.alura.mscompanhiasaereas.dominio;

import org.junit.jupiter.api.Test;

import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.vooComMesmaOrigemEDestino;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class VooTest {

    @Test
    void vooNaoPodeTerOrigemEDestinoNoMesmoAeroporto() {
        assertThatThrownBy(()-> vooComMesmaOrigemEDestino())
                .isInstanceOf(OrigemEDestinoIguaisException.class)
                .hasMessage(OrigemEDestinoIguaisException.MENSAGEM);
    }

}
