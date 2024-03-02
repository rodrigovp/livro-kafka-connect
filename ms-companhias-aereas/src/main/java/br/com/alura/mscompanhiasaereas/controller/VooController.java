package br.com.alura.mscompanhiasaereas.controller;

import br.com.alura.mscompanhiasaereas.service.VooService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voos")
@AllArgsConstructor
public class VooController {

    private VooService service;
    @PostMapping
    ResponseEntity<String> cadastrarNovoVoo(@RequestBody VooDTO vooDTO){
        try{
            service.cadastrarNovo(vooDTO.toVoo());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }   catch (RuntimeException e){
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(e.getMessage());
        }

    }
}
