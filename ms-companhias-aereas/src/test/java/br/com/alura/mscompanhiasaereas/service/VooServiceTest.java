package br.com.alura.mscompanhiasaereas.service;

import br.com.alura.mscompanhiasaereas.dominio.RepositorioDeVoos;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.doRioParaBeloHorizonteAmanha;
import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.doRioParaBeloHorizonteAmanhaComId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VooServiceTest {
    @Mock
    private RepositorioDeVoos repositorioDeVoos;

    @InjectMocks
    private VooService vooService;

    @Test
    void cadastrarNovoVoo() {
        var voo = doRioParaBeloHorizonteAmanha();
        ObjectId idDoVoo = new ObjectId();
        when(repositorioDeVoos.save(voo)).thenReturn(doRioParaBeloHorizonteAmanhaComId(idDoVoo));

        assertThat(vooService.cadastrarNovo(voo)).isEqualTo(idDoVoo.toHexString());
    }

    @Test
    void removerVooExistente() {
        var idDoVoo = new ObjectId();
        when(repositorioDeVoos.existsById(idDoVoo)).thenReturn(true);
        assertThat(vooService.removerVoo(idDoVoo.toHexString())).isTrue();
        verify(repositorioDeVoos).deleteById(idDoVoo);
    }

    @Test
    void tentarRemoverVooInexistente(){
        var idDoVoo = new ObjectId();
        when(repositorioDeVoos.existsById(idDoVoo)).thenReturn(false);
        assertThat(vooService.removerVoo(idDoVoo.toHexString())).isFalse();
        verify(repositorioDeVoos, never()).deleteById(idDoVoo);
    }
}
