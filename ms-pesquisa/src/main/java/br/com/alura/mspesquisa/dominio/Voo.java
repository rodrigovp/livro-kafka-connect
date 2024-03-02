package br.com.alura.mspesquisa.dominio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "voos")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = "id")
public class Voo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Embedded
    private IdentificadorCompanhiaAerea idCompanhiaAerea;

    @Embedded
    private Origem origem;

    @Embedded
    private Destino destino;

    @Enumerated(EnumType.STRING)
    private CompanhiaAerea companhiaAerea;

    @Embedded
    private Preco preco;

    public Voo(IdentificadorCompanhiaAerea idCompanhiaAerea,
               Origem origem, Destino destino,
               CompanhiaAerea companhiaAerea,
               Preco preco) {
        this.idCompanhiaAerea = idCompanhiaAerea;
        this.origem = origem;
        this.destino = destino;
        this.companhiaAerea = companhiaAerea;
        this.preco = preco;
    }

    public Aeroporto destino() {
        return destino.aeroporto();
    }

    public BigDecimal calcularTotalAdultos(int adultos) {
        return preco.paraAdultos(adultos);
    }

    public BigDecimal calcularTotalCriancas(int criancas) {
        return preco.paraCriancas(criancas);
    }
}
