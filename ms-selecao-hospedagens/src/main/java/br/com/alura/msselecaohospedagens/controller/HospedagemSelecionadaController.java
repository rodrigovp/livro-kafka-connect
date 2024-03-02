package br.com.alura.msselecaohospedagens.controller;

import br.com.alura.msselecaohospedagens.controller.dto.HospedagemSelecionadaDTO;
import br.com.alura.msselecaohospedagens.dominio.RepositorioDeHospedagens;
import br.com.alura.msselecaohospedagens.dominio.RepositorioDeHospedagensSelecionadas;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hospedagens-selecionadas")
@AllArgsConstructor
public class HospedagemSelecionadaController {

	private RepositorioDeHospedagensSelecionadas repositorio;
	private RepositorioDeHospedagens repositorioDeHospedagens;
	
	@PostMapping
	public ResponseEntity<Void> selecionar(@RequestBody HospedagemSelecionadaDTO dto){
		
		var hospedagem = dto.toHospedagemSelecionada(repositorioDeHospedagens);
		repositorio.save(hospedagem);
		
		return ResponseEntity.ok().build();
	}
}
