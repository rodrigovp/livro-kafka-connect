package br.com.alura.mspesquisa.controller.dto;

import br.com.alura.mspesquisa.dominio.IdentificadorCompanhiaAerea;
import br.com.alura.mspesquisa.dominio.RepositorioDeNovosVoos;
import br.com.alura.mspesquisa.dominio.Voo;
import br.com.alura.mspesquisa.dominio.VooSelecionado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.alura.mspesquisa.infra.ObjetosParaTestes.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VooSelecionadoDTOTest {

    @Mock
    private RepositorioDeNovosVoos repositorioDeNovosVoos;

    private IdentificadorCompanhiaAerea idCompanhiaAerea;
    private Voo voo;

    @BeforeEach
    void setUp(){
        idCompanhiaAerea = new IdentificadorCompanhiaAerea("identificador");
        voo = umVoo(idCompanhiaAerea);
    }

    @Test
    void converterParaVooSelecionado(){
        when(repositorioDeNovosVoos.findByIdCompanhiaAerea(idCompanhiaAerea)).thenReturn(Optional.of(voo));
        var vooSelecionadoDTO = fabricar(1, 1, "identificador", cpfString());

        var vooSelecionado = vooSelecionadoDTO.toVooSelecionado(repositorioDeNovosVoos);
        var vooSelecionadoEsperado = new VooSelecionado(voo, 1, 1, cpf());

        assertThat(vooSelecionado).isEqualTo(vooSelecionadoEsperado);
    }

    @Test
    void tentarCriarVooSelecionadoComUmVooQueNaoExiste(){
        when(repositorioDeNovosVoos
                .findByIdCompanhiaAerea(any(IdentificadorCompanhiaAerea.class)))
                .thenReturn(Optional.empty());

        var idInexistente = "id inexistente";
        var vooSelecionadoDTO = fabricar(1, 1, idInexistente, cpfString());

        assertThatThrownBy(() -> vooSelecionadoDTO.toVooSelecionado(repositorioDeNovosVoos))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(idInexistente);
    }
}