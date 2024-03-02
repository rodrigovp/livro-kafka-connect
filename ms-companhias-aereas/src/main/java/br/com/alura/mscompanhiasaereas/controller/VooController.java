package br.com.alura.mscompanhiasaereas.controller;

import br.com.alura.mscompanhiasaereas.service.VooService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voos")
@AllArgsConstructor
public class VooController {

    private VooService service;
    @PostMapping
    ResponseEntity<String> cadastrarNovoVoo(@RequestBody VooDTO vooDTO){
        String body;
        HttpStatus statusCode;
        try{
            body = service.cadastrarNovo(vooDTO.toVoo());
            statusCode = HttpStatus.CREATED;
        }   catch (RuntimeException e){
            body = e.getMessage();
            statusCode = HttpStatus.UNPROCESSABLE_ENTITY;
        }
        return ResponseEntity
                .status(statusCode)
                .body(body);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> removerVoo(@PathVariable String id){
        if(service.removerVoo(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
