package br.com.alura.mspacote.dominio;

import java.math.BigDecimal;

public class ObjetosParaTestes {

    public static final HospedagemSelecionada hospedagemSelecionadaComValores(BigDecimal precoAdulto, BigDecimal precoCrianca) {
        return new HospedagemSelecionada("05432786000189", "76859430766", precoAdulto, precoCrianca,
                "fdCSDsdvRrevergerv", "hostel");
    }

    public static final VooSelecionado vooSelecionadoComValores(BigDecimal total){
        return new VooSelecionado(1, "76859430766", 2, "fdCSDsdvRrevergerv", "SDU",
                total, BigDecimal.ONE, BigDecimal.ZERO);
    }
}
