package br.com.alura.mscompanhiasaereas.service;

import br.com.alura.mscompanhiasaereas.dominio.RepositorioDeVoos;
import br.com.alura.mscompanhiasaereas.dominio.Voo;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VooService {

    private RepositorioDeVoos repositorioDeVoos;
    public String cadastrarNovo(Voo voo){
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
