package br.com.alura.mspesquisa.controller;

import br.com.alura.mspesquisa.dominio.*;
import br.com.alura.mspesquisa.service.SelecaoDeVoos;
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

import static br.com.alura.mspesquisa.dominio.DateUtils.localDateTimeOf;
import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.umVoo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VooController.class)
@ActiveProfiles("test")
@Import(ModelMapperFactory.class)
class VooControllerTest {

    @MockBean
    private SelecaoDeVoos selecaoDeVoos;

    @MockBean
    private RepositorioDeNovosVoos repositorioDeNovosVoos;

    @Autowired
    private MockMvc mockMvc;

    private Voo voo;
    private IdentificadorCompanhiaAerea idCompanhiaAerea;

    @BeforeEach
    void setUp(){
        idCompanhiaAerea = new IdentificadorCompanhiaAerea("identificador");
        voo = umVoo(idCompanhiaAerea);
    }

    @Test
    void buscarVoosDisponiveis() throws Exception {
        var origem = new Origem(Aeroporto.CGH, localDateTimeOf("2024-08-21T18:38:39").toLocalDate().atStartOfDay());

        when(selecaoDeVoos.buscar(origem, Aeroporto.SDU)).thenReturn(Set.of(voo));

        mockMvc.perform(get("/voos")
                .param("nome_origem", "CGH")
                .param("data_origem", "2024-08-21")
                .param("nome_destino", "SDU"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idCompanhiaAereaIdentificador").value("identificador"))
                .andExpect(jsonPath("$.[0].origemAeroporto").value("CGH"))
                .andExpect(jsonPath("$.[0].origemPartida").value("2024-08-21T18:38:39"))
                .andExpect(jsonPath("$.[0].destinoAeroporto").value("SDU"))
                .andExpect(jsonPath("$.[0].destinoChegada").value("2024-08-21T20:38:39"))
                .andExpect(jsonPath("$.[0].companhiaAerea").value("AZUL"))
                .andExpect(jsonPath("$.[0].precoAdulto").value(1000))
                .andExpect(jsonPath("$.[0].precoCrianca").value(500))
        ;
    }

    @Test
    void buscarVoosDisponiveisSemParametros() throws Exception {
        mockMvc.perform(get("/voos"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buscarVoosDisponiveisSemParametrosObrigatorios() throws Exception {
        mockMvc.perform(get("/voos")
                .param("nome_origem", "CGH")
                .param("data_origem", "2024-08-21"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/voos")
                        .param("nome_origem", "CGH")
                        .param("nome_destino", "SDU"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/voos")
                        .param("data_origem", "2024-08-21"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buscarVoosDisponiveisComParametrosInvalidos() throws Exception {
        mockMvc.perform(get("/voos")
                        .param("nome_origem", "XXX")
                        .param("data_origem", "2024-08-21")
                        .param("nome_destino", "SDU"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/voos")
                        .param("nome_origem", "CGH")
                        .param("data_origem", "2024-08-39")
                        .param("nome_destino", "SDU"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/voos")
                        .param("nome_origem", "CGH")
                        .param("data_origem", "2024-08-21")
                        .param("nome_destino", "YYY"))
                .andExpect(status().isBadRequest());
    }
}