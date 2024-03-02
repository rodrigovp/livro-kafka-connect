package br.com.alura.mscompanhiasaereas.controller;

import br.com.alura.mscompanhiasaereas.dominio.OrigemEDestinoIguaisException;
import br.com.alura.mscompanhiasaereas.dominio.Voo;
import br.com.alura.mscompanhiasaereas.service.VooService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VooController.class)
class VooControllerTest {

    @MockBean
    private VooService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void cadastrarNovoVoo() throws Exception {
        var vooDto = vooDtoDeSaoPauloAoRioAmanha();
        var voo = vooDto.toVoo();
        var vooDtoJson = toJson(vooDto);

        when(service.cadastrarNovo(voo)).thenReturn("id");
        mockMvc.perform(post("/voos")
                        .content(vooDtoJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("id"));
    }

    @Test
    void tentarCadastrarVooComErroNasInformacoes() throws Exception {
        var vooDto = vooDtoComMesmaOrigemEDestino();
        var vooDtoJson = toJson(vooDto);
        mockMvc.perform(post("/voos")
                        .content(vooDtoJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(OrigemEDestinoIguaisException.MENSAGEM));

        verify(service, never()).cadastrarNovo(any(Voo.class));
    }

    @Test
    void removerVooExistente() throws Exception {
        ObjectId id = new ObjectId();
        when(service.removerVoo(id.toHexString())).thenReturn(true);

        mockMvc.perform(delete("/voos/" + id.toHexString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void tentarRemoverVooInexistente() throws Exception {
        ObjectId id = new ObjectId();
        when(service.removerVoo(id.toHexString())).thenReturn(false);

        mockMvc.perform(delete("/voos/" + id.toHexString()))
                .andExpect(status().isNotFound());
    }
}
