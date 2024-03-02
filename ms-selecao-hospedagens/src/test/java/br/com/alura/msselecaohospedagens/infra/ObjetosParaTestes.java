package br.com.alura.msselecaohospedagens.infra;

import br.com.alura.msselecaohospedagens.controller.dto.HospedagemSelecionadaDTO;
import br.com.alura.msselecaohospedagens.dominio.Aeroporto;
import br.com.alura.msselecaohospedagens.dominio.Diaria;
import br.com.alura.msselecaohospedagens.dominio.Hospedagem;
import br.com.alura.msselecaohospedagens.dominio.TipoHospedagem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.util.ReflectionTestUtils.setField;

public class ObjetosParaTestes {
    
    public static final Hospedagem umaHospedagem(String cnpj, Aeroporto aeroportoMaisProximo) {
    	return new Hospedagem(cnpj, "Pousada do Javeiro", 
    			TipoHospedagem.HOSTEL, aeroportoMaisProximo,
    			new Diaria(new BigDecimal("100.00"), new BigDecimal("50.00")));
    }
    
    public static final HospedagemSelecionadaDTO hospedagemSelecionadaDto(String cnpj, String idCompanhiaAerea, String cpf) {
    	var dto = new HospedagemSelecionadaDTO();
    	setField(dto, "cnpj", cnpj);
    	setField(dto, "idCompanhiaAerea", idCompanhiaAerea);
    	setField(dto, "cpf", cpf);
    	return dto;
    }
    
    public static final String toJson(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
