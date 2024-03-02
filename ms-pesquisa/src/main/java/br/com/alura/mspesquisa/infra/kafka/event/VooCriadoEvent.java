package br.com.alura.mspesquisa.infra.kafka.event;

import br.com.alura.mspesquisa.dominio.CompanhiaAerea;
import br.com.alura.mspesquisa.dominio.IdentificadorCompanhiaAerea;
import br.com.alura.mspesquisa.dominio.Voo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class VooCriadoEvent {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("origem")
    private OrigemEvent origem;

    @JsonProperty("destino")
    private DestinoEvent destino;

    @JsonProperty("companhiaAerea")
    private String companhiaAerea;

    @JsonProperty("preco")
    private PrecoEvent preco;

    public Voo toVoo() {
        return new Voo(
                new IdentificadorCompanhiaAerea(id),
                origem.toOrigem(),
                destino.toDestino(),
                CompanhiaAerea.valueOf(companhiaAerea),
                preco.toPreco());
    }
}
