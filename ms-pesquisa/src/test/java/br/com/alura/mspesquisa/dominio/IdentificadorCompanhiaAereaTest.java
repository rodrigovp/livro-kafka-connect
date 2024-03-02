package br.com.alura.mspesquisa.dominio;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdentificadorCompanhiaAereaTest {

    @Test
    void converterDeJsonParaObjeto(){
        var idVooString = "{\"id\":\"65136e3571e71b6bcc8b3021\"}";
        var idVoo = IdentificadorCompanhiaAerea.fromJson(idVooString);
        var idVooEsperado = new IdentificadorCompanhiaAerea("65136e3571e71b6bcc8b3021");

        assertThat(idVoo).isEqualTo(idVooEsperado);
    }
}