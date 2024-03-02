package br.com.alura.mspesquisa.controller;

import br.com.alura.mspesquisa.dominio.IdentificadorCompanhiaAerea;
import br.com.alura.mspesquisa.dominio.RepositorioDeNovosVoos;
import br.com.alura.mspesquisa.dominio.Voo;
import br.com.alura.mspesquisa.dominio.VooSelecionado;
import br.com.alura.mspesquisa.service.SelecaoDeVoos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.*;
import static java.util.Optional.empty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VooSelecionadoController.class)
@ActiveProfiles("test")
class VooSelecionadoControllerTest {

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
    void selecionarUmVoo() throws Exception {
        when(repositorioDeNovosVoos.findByIdCompanhiaAerea(idCompanhiaAerea)).thenReturn(Optional.of(voo));

        mockMvc.perform(post("/voos-selecionados")
                        .content(toJson(fabricar(1,1, "identificador", cpfString())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(selecaoDeVoos).selecionar(any(VooSelecionado.class));
    }

    @Test
    void tentarSelecionarVooInexistente() throws Exception {
        when(repositorioDeNovosVoos.findByIdCompanhiaAerea(idCompanhiaAerea)).thenReturn(empty());

        mockMvc.perform(post("/voos-selecionados")
                        .content(toJson(fabricar(1,1, "identificador", cpfString())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify(selecaoDeVoos, never()).selecionar(any(VooSelecionado.class));
    }

    @Test
    void tentarSelecionarVooComCpfInvalido() throws Exception{
        mockMvc.perform(post("/voos-selecionados")
                        .content(toJson(fabricar(1,1, "identificador", "67545659566")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify(selecaoDeVoos, never()).selecionar(any(VooSelecionado.class));
    }
}