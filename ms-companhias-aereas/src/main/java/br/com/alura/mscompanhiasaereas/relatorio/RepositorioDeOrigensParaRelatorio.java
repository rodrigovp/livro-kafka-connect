package br.com.alura.mscompanhiasaereas.relatorio;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioDeOrigensParaRelatorio extends MongoRepository<OrigemParaRelatorio, ObjectId> {
}
