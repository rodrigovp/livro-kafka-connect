package br.com.alura.mscompanhiasaereas.dominio;

import org.junit.jupiter.api.Test;

import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.amanha;
import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.passado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DestinoTest {

    @Test
    void chegadaDeveConterAnoMesDiaHoraMinutoESegundo(){
        var destino = new Destino(Aeroporto.CGH, amanha());

        assertThat(destino.chegada()).hasNano(0);
    }

    @Test
    void chegadasNaoPodemSerFeitasNoPassado() {
        assertThatThrownBy(() ->
                new Destino(Aeroporto.CGH, passado()))
                .isInstanceOf(HorarioNoPassadoException.class)
                .hasMessage(HorarioNoPassadoException.MENSAGEM);
    }

    @Test
    void verificarSeMesmoLocalQueOrigem() {
        var beloHorizonte = Aeroporto.BSB;
        var origem = new Origem(beloHorizonte, amanha());
        var destino = new Destino(beloHorizonte, amanha());

        assertThat(destino.mesmoAeroportoDa(origem)).isTrue();
    }
}