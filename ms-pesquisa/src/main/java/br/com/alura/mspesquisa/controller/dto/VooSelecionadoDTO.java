package br.com.alura.mspesquisa.controller.dto;

import br.com.alura.mspesquisa.dominio.CpfComprador;
import br.com.alura.mspesquisa.dominio.IdentificadorCompanhiaAerea;
import br.com.alura.mspesquisa.dominio.RepositorioDeNovosVoos;
import br.com.alura.mspesquisa.dominio.VooSelecionado;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.lang.String.format;

public class VooSelecionadoDTO {

    @JsonProperty
    private String idCompanhiaAerea;

    @JsonProperty
    private int adultos;

    @JsonProperty
    private int criancas;

    @JsonProperty
    private String cpfComprador;

    public VooSelecionado toVooSelecionado(RepositorioDeNovosVoos repositorioDeNovosVoos) {
        var voo = repositorioDeNovosVoos
                .findByIdCompanhiaAerea(new IdentificadorCompanhiaAerea(idCompanhiaAerea))
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(format("Voo %s não encontrado", idCompanhiaAerea));
                });
        return new VooSelecionado(voo, adultos, criancas, new CpfComprador(cpfComprador));
    }
}
