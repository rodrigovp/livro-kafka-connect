package br.com.alura.mspesquisa.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Embeddable
public record Origem(
        @Column(name = "embarque")
        @Enumerated(EnumType.STRING)
        Aeroporto aeroporto,
        LocalDateTime partida) {

    public Origem {
        partida = partida.truncatedTo(ChronoUnit.SECONDS);
    }
}
