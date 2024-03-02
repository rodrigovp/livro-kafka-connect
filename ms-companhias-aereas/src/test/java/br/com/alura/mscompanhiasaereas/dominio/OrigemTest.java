package br.com.alura.mscompanhiasaereas.dominio;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.passado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrigemTest {

    @Test
    void partidaDeveConterAnoMesDiaHoraMinutoESegundo(){
        var origem = new Origem(Aeroporto.CGH, LocalDateTime.now().plusDays(1));

        assertThat(origem.partida()).hasNano(0);
    }

    @Test
    void partidasNaoPodemSerFeitasNoPassado() {
        assertThatThrownBy(() ->
                new Origem(Aeroporto.CGH, passado()))
                .isInstanceOf(HorarioNoPassadoException.class)
                .hasMessage(HorarioNoPassadoException.MENSAGEM);
    }
}