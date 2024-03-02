package br.com.alura.mspesquisa.service;

import br.com.alura.mspesquisa.dominio.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class SelecaoDeVoos {

    private RepositorioDeNovosVoos repositorioDeNovosVoos;
    private RepositorioDeVoosSelecionados repositorioDeVoosSelecionados;

    public Set<Voo> buscar(Origem origem, Aeroporto destino) {
        return repositorioDeNovosVoos.findByOrigemAeroportoAndOrigemPartidaBetweenAndDestinoAeroporto(
                origem.aeroporto(),
                origem.partida().toLocalDate().atStartOfDay(),
                origem.partida().toLocalDate().plusDays(1).atStartOfDay(),
                destino
        );
    }

    public void selecionar(VooSelecionado vooSelecionado) {
        repositorioDeVoosSelecionados.save(vooSelecionado);
    }
}
