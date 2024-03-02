package br.com.alura.mscompanhiasaereas.service;

import br.com.alura.mscompanhiasaereas.dominio.RepositorioDeVoos;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.doRioParaBeloHorizonteAmanha;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VooServiceTest {
    @Mock
    private RepositorioDeVoos repositorioDeVoos;

    @InjectMocks
    private VooService vooService;

    @Test
    void cadastrarNovoVoo() {
        var voo = doRioParaBeloHorizonteAmanha();
        vooService.cadastrarNovo(voo);

        verify(repositorioDeVoos).save(voo);
    }
}