package br.com.alura.mscompanhiasaereas.dominio;

import br.com.alura.mscompanhiasaereas.controller.VooDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static br.com.alura.mscompanhiasaereas.dominio.Aeroporto.CGH;
import static br.com.alura.mscompanhiasaereas.dominio.Aeroporto.SDU;
import static br.com.alura.mscompanhiasaereas.dominio.CompanhiaAerea.GOL;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class ObjetosParaTestes {

    public static final LocalDateTime hoje(){
        return LocalDateTime.now();
    }

    public static final LocalDateTime amanha(){
        return hoje().plusDays(1);
    }

    public static final LocalDateTime ontem(){
        return hoje().minusDays(1);
    }

    public static final LocalDateTime passado(){
        return hoje().minusSeconds(1);
    }

    public static final Preco preco(){
        return new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00"));
    }

    public static final Voo doRioParaBeloHorizonteAmanha(){
        var rioDeJaneiro = new Origem(SDU, amanha());
        var beloHorizonte = new Destino(Aeroporto.BSB, amanha().plusHours(3));
        return new Voo(rioDeJaneiro, beloHorizonte, CompanhiaAerea.AZUL, preco());
    }

    public static final Voo doRioParaBeloHorizonteAmanhaComId(ObjectId id){
        var voo = doRioParaBeloHorizonteAmanha();
        setField(voo, "id", id);
        return voo;
    }
    public static final Origem origemCongonhasAmanha(){
        return new Origem(CGH, amanha());
    }

    public static final Destino destinoCongonhasAmanha(){
        return new Destino(CGH, amanha());
    }

    public static final Voo vooComMesmaOrigemEDestino() {
        return new Voo(origemCongonhasAmanha(), destinoCongonhasAmanha(), GOL, preco());
    }

    public static final VooDTO vooDtoComMesmaOrigemEDestino(){
        return vooDto(CGH, CGH, amanha(), amanha().plusHours(1), GOL, preco());
    }

    public static final VooDTO vooDto(Aeroporto origem, Aeroporto destino,
                                      LocalDateTime partida, LocalDateTime chegada,
                                      CompanhiaAerea companhiaAerea, Preco preco){
        VooDTO vooDTO = new VooDTO();
        setField(vooDTO, "origem", origem.toString());
        setField(vooDTO, "destino", destino.toString());
        setField(vooDTO, "partida", partida.toString());
        setField(vooDTO, "chegada", chegada.toString());
        setField(vooDTO, "companhiaAerea", companhiaAerea.toString());
        setField(vooDTO, "precoAdulto", preco.adulto());
        setField(vooDTO, "precoCrianca", preco.crianca());
        return vooDTO;
    }

    public static final VooDTO vooDtoDeSaoPauloAoRioAmanha(){
        return vooDto(CGH, SDU, amanha(), amanha().plusHours(1), GOL, preco());
    }

    public static final String toJson(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
