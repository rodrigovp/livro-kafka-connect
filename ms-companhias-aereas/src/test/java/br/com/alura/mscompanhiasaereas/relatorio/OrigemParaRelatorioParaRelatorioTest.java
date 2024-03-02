package br.com.alura.mscompanhiasaereas.relatorio;

import org.junit.jupiter.api.Test;

import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.origemCongonhasAmanha;
import static org.assertj.core.api.Assertions.assertThat;

class OrigemParaRelatorioParaRelatorioTest {

    @Test
    void criarUmaOrigemParaRelatorioAPartirDeUmaOrigem(){
        var origem = origemCongonhasAmanha();
        var origemParaRelatorio = new OrigemParaRelatorio(origem);

        assertThat(origemParaRelatorio).extracting("siglaAeroporto").isEqualTo(origem.aeroporto().name());
        assertThat(origemParaRelatorio).extracting("partida").isEqualTo(origem.partida());
    }
}
