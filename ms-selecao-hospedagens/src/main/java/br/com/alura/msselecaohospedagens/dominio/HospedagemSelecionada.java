package br.com.alura.msselecaohospedagens.dominio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "hospedagens_selecionadas")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HospedagemSelecionada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cnpj;
	
	private String nome;
	
	@Embedded
	private Diaria diaria;

	@Embedded
	private IdentificadorCompanhiaAerea idCompanhiaAerea;

	@Embedded
	private CpfComprador cpf;
	
	public HospedagemSelecionada(String cnpj, String nome, 
			Diaria diaria, 
			IdentificadorCompanhiaAerea idCompanhiaAerea, CpfComprador cpf) {
		this.cnpj = cnpj;
		this.nome = nome;
		this.diaria = diaria;
		this.idCompanhiaAerea = idCompanhiaAerea;
		this.cpf = cpf;
	}
}
