package br.com.alura.mscompanhiasaereas.dominio;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record Origem(Aeroporto aeroporto,
                     LocalDateTime partida) {

    public Origem {
        if (partida.isBefore(LocalDateTime.now())) throw new HorarioNoPassadoException();
        partida = partida.truncatedTo(ChronoUnit.SECONDS);
    }
}
