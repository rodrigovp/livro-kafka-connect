package br.com.alura.mspesquisa.controller;

import br.com.alura.mspesquisa.controller.dto.VooSelecionadoDTO;
import br.com.alura.mspesquisa.dominio.RepositorioDeNovosVoos;
import br.com.alura.mspesquisa.service.SelecaoDeVoos;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voos-selecionados")
@AllArgsConstructor
public class VooSelecionadoController {

    private RepositorioDeNovosVoos repositorioDeNovosVoos;
    private SelecaoDeVoos selecaoDeVoos;

    @PostMapping
    public ResponseEntity<Void> selecionarVoo(@RequestBody VooSelecionadoDTO vooSelecionadoDTO){
        try {
            var vooSelecionado = vooSelecionadoDTO.toVooSelecionado(repositorioDeNovosVoos);
            selecaoDeVoos.selecionar(vooSelecionado);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
