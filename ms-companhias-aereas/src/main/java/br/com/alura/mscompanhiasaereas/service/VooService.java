package br.com.alura.mscompanhiasaereas.service;

import br.com.alura.mscompanhiasaereas.dominio.RepositorioDeVoos;
import br.com.alura.mscompanhiasaereas.dominio.Voo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VooService {

    private RepositorioDeVoos repositorioDeVoos;
    public void cadastrarNovo(Voo voo){
        repositorioDeVoos.save(voo);
    }
}
