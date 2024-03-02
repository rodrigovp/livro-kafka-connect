package br.com.alura.msselecaohospedagens.controller;

import br.com.alura.msselecaohospedagens.dominio.Aeroporto;
import br.com.alura.msselecaohospedagens.dominio.Hospedagem;
import br.com.alura.msselecaohospedagens.service.SelecaoDeHospedagens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static br.com.alura.msselecaohospedagens.infra.ObjetosParaTestes.umaHospedagem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HospedagemController.class)
@ActiveProfiles("test")
@Import(ModelMapperFactory.class)
public class HospedagemControllerTest {

    @MockBean
    private SelecaoDeHospedagens selecaoDeHospedagens;
    
    @Autowired
    private MockMvc mockMvc;

    private Aeroporto aeroportoMaisProximo;
    private Hospedagem hospedagem;

    @BeforeEach
    void setUp(){
        aeroportoMaisProximo = Aeroporto.CGH;
        hospedagem = umaHospedagem("74258327000102", aeroportoMaisProximo);
    }

    @Test
    void buscarHospedagensDisponiveis() throws Exception {
        when(selecaoDeHospedagens.buscarPor(aeroportoMaisProximo)).thenReturn(Set.of(hospedagem));

        mockMvc.perform(get("/hospedagens")
                .param("nome_destino", aeroportoMaisProximo.name()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nome").value("Pousada do Javeiro"))
                .andExpect(jsonPath("$.[0].tipo").value("HOSTEL"))
                .andExpect(jsonPath("$.[0].aeroportoMaisProximo").value(aeroportoMaisProximo.name()))
                .andExpect(jsonPath("$.[0].diariaAdulto").value(100))
                .andExpect(jsonPath("$.[0].diariaCrianca").value(50))
        ;
    }

    @Test
    void buscarVoosDisponiveisSemParametros() throws Exception {
        mockMvc.perform(get("/hospedagens"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buscarVoosDisponiveisComParametrosInvalidos() throws Exception {
        mockMvc.perform(get("/hospedagens")
                        .param("nome_destino", "YYY"))
                .andExpect(status().isBadRequest());
    }
}
