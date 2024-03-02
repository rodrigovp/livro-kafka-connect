package br.com.alura.msselecaohospedagens.service;

import br.com.alura.msselecaohospedagens.dominio.Aeroporto;
import br.com.alura.msselecaohospedagens.dominio.Hospedagem;
import br.com.alura.msselecaohospedagens.dominio.RepositorioDeHospedagens;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class SelecaoDeHospedagens {

	private RepositorioDeHospedagens repositorioDeHospedagens;
	
	public Set<Hospedagem> buscarPor(Aeroporto aeroporto) {
		return repositorioDeHospedagens.findByAeroportoMaisProximo(aeroporto);
	}

}
