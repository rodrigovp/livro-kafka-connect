package br.com.alura.mscompanhiasaereas.service;

import br.com.alura.mscompanhiasaereas.dominio.RepositorioDeVoos;
import br.com.alura.mscompanhiasaereas.dominio.Voo;
import br.com.alura.mscompanhiasaereas.relatorio.RepositorioDeOrigensParaRelatorio;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.alura.mscompanhiasaereas.relatorio.OrigemParaRelatorio.origemDoVoo;

@Service
@AllArgsConstructor
public class VooService {

    private RepositorioDeVoos repositorioDeVoos;
    private RepositorioDeOrigensParaRelatorio repositorioDeOrigensParaRelatorio;

    @Transactional
    public String cadastrarNovo(Voo voo){
        repositorioDeOrigensParaRelatorio.save(origemDoVoo(voo));
        return repositorioDeVoos.save(voo).getId().toHexString();
    }

    public boolean removerVoo(String idVooString){
        ObjectId idVoo = new ObjectId(idVooString);
        if(repositorioDeVoos.existsById(idVoo)){
            repositorioDeVoos.deleteById(idVoo);
            return true;
        }
        return false;
    }
}
