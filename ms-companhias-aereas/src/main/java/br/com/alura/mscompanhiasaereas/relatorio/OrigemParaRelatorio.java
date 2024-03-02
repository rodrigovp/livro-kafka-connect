package br.com.alura.mscompanhiasaereas.relatorio;

import br.com.alura.mscompanhiasaereas.dominio.Origem;
import br.com.alura.mscompanhiasaereas.dominio.Voo;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "origens_relatorio")
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrigemParaRelatorio {

    @Id
    private ObjectId id;
    private String siglaAeroporto;
    private LocalDateTime partida;

    private OrigemParaRelatorio(String siglaAeroporto, LocalDateTime partida){
        this.siglaAeroporto = siglaAeroporto;
        this.partida = partida;
    }

    OrigemParaRelatorio(Origem origem){
        this(origem.aeroporto().name(), origem.partida());
    }

    public static final OrigemParaRelatorio origemDoVoo(Voo voo){
        return new OrigemParaRelatorio(voo.deOrigem());
    }
}
