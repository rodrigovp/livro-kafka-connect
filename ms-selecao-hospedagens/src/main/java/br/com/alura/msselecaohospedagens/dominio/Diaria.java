package br.com.alura.msselecaohospedagens.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public record Diaria(
		@Column(name = "valor_adulto")
		BigDecimal adulto,
		@Column(name = "valor_crianca")            
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
