package br.com.alura.mscompanhiasaereas.dominio;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "voos")
@EqualsAndHashCode(exclude = "id")
public class Voo {

    @Id
    @Getter
    private ObjectId id;

    private Origem origem;
    private Destino destino;
    private CompanhiaAerea companhiaAerea;
    private Preco preco;

    public Voo(Origem origem, Destino destino, CompanhiaAerea companhiaAerea,
               Preco preco) {
        if (destino.mesmoAeroportoDa(origem)) throw new OrigemEDestinoIguaisException();
        this.origem = origem;
        this.destino = destino;
        this.companhiaAerea = companhiaAerea;
        this.preco = preco;
    }
}
