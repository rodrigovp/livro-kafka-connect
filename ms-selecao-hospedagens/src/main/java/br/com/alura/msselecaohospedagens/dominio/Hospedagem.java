package br.com.alura.msselecaohospedagens.dominio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hospedagens")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of="cnpj")
public class Hospedagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Getter
	private String cnpj;
	
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private TipoHospedagem tipo;
	
	@Enumerated(EnumType.STRING)
	private Aeroporto aeroportoMaisProximo;
	
	@Embedded
	private Diaria diaria;
	
	public Hospedagem(String cnpj, String nome, TipoHospedagem tipo, Aeroporto aeroportoMaisProximo, Diaria diaria) {
		this.cnpj = cnpj;
		this.nome = nome;
		this.tipo = tipo;
		this.aeroportoMaisProximo = aeroportoMaisProximo;
		this.diaria = diaria;
	}
	
	public HospedagemSelecionada selecionar(IdentificadorCompanhiaAerea idCompanhiaAerea, CpfComprador cpfComprador) {
		return new HospedagemSelecionada(cnpj, nome, diaria, idCompanhiaAerea, cpfComprador);
	}
}
