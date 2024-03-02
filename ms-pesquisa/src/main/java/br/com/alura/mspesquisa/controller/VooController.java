package br.com.alura.mspesquisa.controller;

import br.com.alura.mspesquisa.controller.dto.VooDto;
import br.com.alura.mspesquisa.dominio.Aeroporto;
import br.com.alura.mspesquisa.dominio.Origem;
import br.com.alura.mspesquisa.dominio.Voo;
import br.com.alura.mspesquisa.service.SelecaoDeVoos;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/voos")
@AllArgsConstructor
class VooController {

    private ModelMapper modelMapper;
    private SelecaoDeVoos selecaoDeVoos;

    @GetMapping
    public ResponseEntity<Set<VooDto>> listarVoos(
            @RequestParam("nome_origem") String nomeOrigem,
            @RequestParam("data_origem") LocalDate dataDePartida,
            @RequestParam("nome_destino") String nomeDestino){
        try{
            var origem = new Origem(Aeroporto.valueOf(nomeOrigem), dataDePartida.atStartOfDay());
            var destino = Aeroporto.valueOf(nomeDestino);

            var voosDto = selecaoDeVoos.buscar(origem, destino).stream().map(this::convertToDto).collect(toSet());
            return ResponseEntity.ok().body(voosDto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    private VooDto convertToDto(Voo voo){
        return modelMapper.map(voo, VooDto.class);
    }
}
