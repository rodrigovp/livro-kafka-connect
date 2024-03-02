package br.com.alura.mscompanhiasaereas.dominio;

public class HorarioNoPassadoException extends RuntimeException {

    public static final String MENSAGEM = "Partidas não podem ser feitas no passado";

    public HorarioNoPassadoException() {
        super(MENSAGEM);
    }
}
