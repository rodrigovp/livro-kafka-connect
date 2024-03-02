package br.com.alura.mspesquisa.dominio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "voos_selecionados")
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VooSelecionado {

    @Id
    @GeneratedValue
    private Long id;

    private int adultos;
    private int criancas;

    @Embedded
    private IdentificadorCompanhiaAerea identificadorCompanhiaAerea;

    @Enumerated(EnumType.STRING)
    private Aeroporto destino;

    private BigDecimal totalAdultos;
    private BigDecimal totalCriancas;
    private BigDecimal total;

    @Embedded
    private CpfComprador cpfComprador;

    public VooSelecionado(Voo voo, int adultos, int criancas, CpfComprador cpfComprador){
        this.identificadorCompanhiaAerea = voo.getIdCompanhiaAerea();
        this.adultos = adultos;
        this.criancas = criancas;
        this.destino = voo.destino();
        this.totalAdultos = voo.calcularTotalAdultos(adultos);
        this.totalCriancas = voo.calcularTotalCriancas(criancas);
        this.total = totalAdultos.add(totalCriancas);
        this.cpfComprador = cpfComprador;
    }

    public IdentificadorCompanhiaAerea idCompanhiaAerea() {
        return identificadorCompanhiaAerea;
    }
}
