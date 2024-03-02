package br.com.alura.msselecaohospedagens.controller.dto;

import br.com.alura.msselecaohospedagens.dominio.CpfComprador;
import br.com.alura.msselecaohospedagens.dominio.HospedagemSelecionada;
import br.com.alura.msselecaohospedagens.dominio.IdentificadorCompanhiaAerea;
import br.com.alura.msselecaohospedagens.dominio.RepositorioDeHospedagens;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HospedagemSelecionadaDTO {

	@JsonProperty
	private String cnpj;
	
	@JsonProperty
	private String idCompanhiaAerea;
	
	@JsonProperty
	private String cpf;

	public HospedagemSelecionada toHospedagemSelecionada(RepositorioDeHospedagens repositorioDeHospedagens) {
		return repositorioDeHospedagens
				.findByCnpj(cnpj)
				.orElseThrow(IllegalArgumentException::new)
				.selecionar(new IdentificadorCompanhiaAerea(idCompanhiaAerea), new CpfComprador(cpf));
	}
	
}
