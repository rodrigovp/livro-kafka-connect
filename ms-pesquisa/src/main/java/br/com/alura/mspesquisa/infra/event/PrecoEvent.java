package br.com.alura.mspesquisa.infra.event;

import java.math.BigDecimal;

public record PrecoEvent(BigDecimal adulto,
                         BigDecimal crianca){

}
