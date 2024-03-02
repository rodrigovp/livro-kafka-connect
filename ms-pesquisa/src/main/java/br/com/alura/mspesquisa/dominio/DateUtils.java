package br.com.alura.mspesquisa.dominio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    public static final LocalDateTime localDateTimeOf(String data){
        return LocalDateTime.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
