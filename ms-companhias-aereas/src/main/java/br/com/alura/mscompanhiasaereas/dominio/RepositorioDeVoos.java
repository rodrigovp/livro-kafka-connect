package br.com.alura.mscompanhiasaereas.dominio;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioDeVoos extends MongoRepository<Voo, ObjectId> {


}
