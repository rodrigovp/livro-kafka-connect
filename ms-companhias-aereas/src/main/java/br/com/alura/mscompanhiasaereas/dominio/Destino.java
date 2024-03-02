package br.com.alura.mscompanhiasaereas.dominio;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record Destino(Aeroporto aeroporto,
                      LocalDateTime chegada) {

    public Destino {
        if (chegada.isBefore(LocalDateTime.now())) throw new HorarioNoPassadoException();
        chegada = chegada.truncatedTo(ChronoUnit.SECONDS);
    }

    public boolean mesmoAeroportoDa(Origem origem) {
        return aeroporto.equals(origem.aeroporto());
    }
}
