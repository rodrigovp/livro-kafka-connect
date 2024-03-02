package br.com.alura.msmarketing.dominio;

import java.math.BigDecimal;

class ObjetosParaTestes {

    static final HospedagemSelecionada hospedagemSelecionadaComValores(BigDecimal precoAdulto, BigDecimal precoCrianca) {
        return new HospedagemSelecionada("", "", precoAdulto, precoCrianca,
                "", "");
    }

    static final VooSelecionado vooSelecionadoComValores(BigDecimal total){
        return new VooSelecionado(1, "", 2, "", "",
                total, BigDecimal.ONE, BigDecimal.ZERO);
    }
}
