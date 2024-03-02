package br.com.alura.mspesquisa.infra;

import br.com.alura.mspesquisa.controller.dto.VooSelecionadoDTO;
import br.com.alura.mspesquisa.dominio.*;
import br.com.alura.mspesquisa.infra.kafka.event.VooCriadoEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import static br.com.alura.mspesquisa.dominio.Aeroporto.CGH;
import static br.com.alura.mspesquisa.dominio.Aeroporto.SDU;
import static br.com.alura.mspesquisa.dominio.CompanhiaAerea.AZUL;
import static br.com.alura.mspesquisa.dominio.DateUtils.localDateTimeOf;
import static java.util.UUID.randomUUID;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class ObjetosParaTestes {

    public static final VooCriadoEvent vooCriadoEvent(){
        val objectMapper = new ObjectMapper();
        var vooCriadoEvent = new VooCriadoEvent();

        try {
            vooCriadoEvent = objectMapper.readValue(new File("src/test/resources/voo-criado-event.json"), VooCriadoEvent.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return vooCriadoEvent;
    }

    public static final Voo umVoo(IdentificadorCompanhiaAerea idCompanhiaAerea) {
        return new Voo(
                idCompanhiaAerea,
                new Origem(CGH, localDateTimeOf("2024-08-21T18:38:39")),
                new Destino(SDU, localDateTimeOf("2024-08-21T20:38:39")),
                AZUL,
                new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00"))
        );
    }
    
    public static final IdentificadorCompanhiaAerea identificadorCompanhiaAerea() {
    	var uuidString = randomUUID().toString().replaceAll("-", "");
    	return new IdentificadorCompanhiaAerea(uuidString);
    }

    public static final VooSelecionadoDTO fabricar(int adultos, int criancas, String idCompanhiaAerea, String cpf){
        VooSelecionadoDTO vooSelecionadoDTO = new VooSelecionadoDTO();
        setField(vooSelecionadoDTO, "adultos", adultos);
        setField(vooSelecionadoDTO, "criancas", criancas);
        setField(vooSelecionadoDTO, "idCompanhiaAerea", idCompanhiaAerea);
        setField(vooSelecionadoDTO, "cpfComprador", cpf);
        return vooSelecionadoDTO;
    }

    public static final CpfComprador cpf(){
        return new CpfComprador(cpfString());
    }

    public static final CpfComprador cpf(String cpf){
        return new CpfComprador(cpf);
    }

    public static final String cpfString(){
        return "36884617462";
    }

    public static final String toJson(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
