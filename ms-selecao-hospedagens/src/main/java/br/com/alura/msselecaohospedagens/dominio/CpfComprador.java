package br.com.alura.msselecaohospedagens.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import static br.com.alura.msselecaohospedagens.dominio.ValidadorCpf.isValidCPF;

@Embeddable
public record CpfComprador(@Column(name = "cpf_comprador") String cpf) {

    public CpfComprador(String cpf) {
        if(!isValidCPF(cpf)) throw new IllegalArgumentException();
        this.cpf = cpf;
    }
}
