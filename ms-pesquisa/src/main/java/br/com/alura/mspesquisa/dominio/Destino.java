package br.com.alura.mspesquisa.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Embeddable
public record Destino(
        @Column(name = "desembarque")
        @Enumerated(EnumType.STRING)
        Aeroporto aeroporto,
        LocalDateTime chegada) {

    public Destino {
        chegada = chegada.truncatedTo(ChronoUnit.SECONDS);
    }
}
