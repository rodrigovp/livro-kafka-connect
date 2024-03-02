package br.com.alura.msmarketing.dominio;

import java.math.BigDecimal;

public class ObjetosParaTestes {

    static final HospedagemSelecionada hospedagemSelecionadaComValores(BigDecimal precoAdulto, BigDecimal precoCrianca) {
        return new HospedagemSelecionada("", "", precoAdulto, precoCrianca,
                "", "");
    }

    static final VooSelecionado vooSelecionadoComValores(BigDecimal total){
        return new VooSelecionado(1, "34983483233", 2, "", "",
                total, BigDecimal.ONE, BigDecimal.ZERO);
    }

    static final HospedagemSelecionada hospedagemSelecionadaComCnpj(String cnpj) {
    	return new HospedagemSelecionada(cnpj, "", BigDecimal.ONE, BigDecimal.ONE, "", "");
    }

    public static final Pacote pacoteParaHospedagemComCnpj(String cnpj) {
    	return new Pacote(vooSelecionadoComValores(BigDecimal.TEN), hospedagemSelecionadaComCnpj(cnpj));
    }

    public static final Hospedagem hospedagemComCnpj(String cnpj) {
    	return new Hospedagem(1L, "", cnpj, BigDecimal.ONE, BigDecimal.ONE, "", "");
    }
}
