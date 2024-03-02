package br.com.alura.mscompanhiasaereas.dominio;

public class OrigemEDestinoIguaisException extends RuntimeException{

    public static final String MENSAGEM = "Origem e destino não podem ser o mesmo";
    public OrigemEDestinoIguaisException() {
        super(MENSAGEM);
    }
}
