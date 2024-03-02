package br.com.alura.mspesquisa.infra.kafka.event;

import br.com.alura.mspesquisa.dominio.Preco;

import java.math.BigDecimal;

public record PrecoEvent(BigDecimal adulto,
                         BigDecimal crianca){

    public Preco toPreco() {
        return new Preco(adulto.setScale(2), crianca.setScale(2));
    }
}
