package br.com.alura.mspesquisa.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Embeddable;

@Embeddable
@JsonSerialize
public record IdentificadorCompanhiaAerea(
        @JsonProperty("id") String identificador) {

    public static IdentificadorCompanhiaAerea fromJson(String idVoo) {
        try{
            return new ObjectMapper().readValue(idVoo, IdentificadorCompanhiaAerea.class);
        }catch (JsonProcessingException e){
           throw new RuntimeException(e);
        }
    }
}
