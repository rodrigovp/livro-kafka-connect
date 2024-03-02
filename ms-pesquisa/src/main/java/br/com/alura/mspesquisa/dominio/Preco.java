package br.com.alura.mspesquisa.dominio;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public record Preco(BigDecimal adulto,
                    BigDecimal crianca){

    public BigDecimal paraAdultos(int adultos) {
        return multiplicar(adulto, adultos);
    }

    public BigDecimal paraCriancas(int criancas) {
        return multiplicar(crianca, criancas);
    }

    private BigDecimal multiplicar(BigDecimal valor, int quantidade){
        return valor.multiply(new BigDecimal(quantidade)).setScale(2);
    }
}
