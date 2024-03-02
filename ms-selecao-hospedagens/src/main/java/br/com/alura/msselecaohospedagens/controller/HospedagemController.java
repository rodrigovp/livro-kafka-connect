package br.com.alura.msselecaohospedagens.controller;

import br.com.alura.msselecaohospedagens.controller.dto.HospedagemDTO;
import br.com.alura.msselecaohospedagens.dominio.Aeroporto;
import br.com.alura.msselecaohospedagens.dominio.Hospedagem;
import br.com.alura.msselecaohospedagens.service.SelecaoDeHospedagens;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/hospedagens")
@AllArgsConstructor
public class HospedagemController {

	private ModelMapper modelMapper;
	private SelecaoDeHospedagens selecaoDeHospedagens;
	
	@GetMapping
	public ResponseEntity<Set<HospedagemDTO>> listarHospedagens(@RequestParam("nome_destino") String nomeDestino){
		try {
			var aeroportoDestino = Aeroporto.valueOf(nomeDestino);
			var hospedagensDto = selecaoDeHospedagens.buscarPor(aeroportoDestino).stream().map(this::convertToDto).collect(toSet());
			return ResponseEntity.ok().body(hospedagensDto);
		}catch(IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
    private HospedagemDTO convertToDto(Hospedagem hospedagem){
        return modelMapper.map(hospedagem, HospedagemDTO.class);
    }
}
