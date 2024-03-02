package br.com.alura.mscompanhiasaereas.controller;

import br.com.alura.mscompanhiasaereas.dominio.Destino;
import br.com.alura.mscompanhiasaereas.dominio.HorarioNoPassadoException;
import br.com.alura.mscompanhiasaereas.dominio.Origem;
import br.com.alura.mscompanhiasaereas.dominio.Voo;
import org.junit.jupiter.api.Test;

import static br.com.alura.mscompanhiasaereas.dominio.Aeroporto.CGH;
import static br.com.alura.mscompanhiasaereas.dominio.Aeroporto.SDU;
import static br.com.alura.mscompanhiasaereas.dominio.CompanhiaAerea.GOL;
import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VooDTOTest {

    @Test
    void converterParaVoo() {
        var partida = amanha();
        var chegada = amanha().plusHours(2);

        var dto = vooDto(CGH, SDU, partida, chegada, GOL, preco());

        var voo = dto.toVoo();
        var vooEsperado = new Voo(new Origem(CGH, partida), new Destino(SDU, chegada), GOL, preco());
        assertThat(voo).isEqualTo(vooEsperado);
    }

    @Test
    void lancarExcecaoQuandoPartidaForNoPassado() {
        var partida = ontem();
        var chegada = amanha();

        var dto = vooDto(CGH, SDU, partida, chegada, GOL, preco());

        assertThatThrownBy(dto::toVoo)
                .isInstanceOf(HorarioNoPassadoException.class)
                .hasMessage(HorarioNoPassadoException.MENSAGEM);
    }
}