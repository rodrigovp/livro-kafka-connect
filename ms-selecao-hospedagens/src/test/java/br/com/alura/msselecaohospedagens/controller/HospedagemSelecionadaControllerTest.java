package br.com.alura.msselecaohospedagens.controller;

import br.com.alura.msselecaohospedagens.dominio.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static br.com.alura.msselecaohospedagens.infra.ObjetosParaTestes.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HospedagemSelecionadaController.class)
@ActiveProfiles("test")
class HospedagemSelecionadaControllerTest {

    @MockBean
    private RepositorioDeHospedagensSelecionadas repositorio;
    
    @MockBean
    private RepositorioDeHospedagens repositorioDeHospedagens;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void selecionarHospedagem() throws Exception {
    	var cnpj = "37827045000144";
    	var idCompAereaString = "id";
    	var cpfString = "53695724889";
    	var umaHospedagem = umaHospedagem(cnpj, Aeroporto.BSB);
    	var hospedagemSelecionada = umaHospedagem.selecionar( 
    			new IdentificadorCompanhiaAerea(idCompAereaString), 
    			new CpfComprador(cpfString));
    	when(repositorioDeHospedagens.findByCnpj(cnpj)).thenReturn(Optional.of(umaHospedagem));
    	
    	mockMvc.perform(post("/hospedagens-selecionadas")
                .content(toJson(hospedagemSelecionadaDto(cnpj, idCompAereaString, cpfString)))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

        verify(repositorio).save(hospedagemSelecionada);

    }
}