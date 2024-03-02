package br.com.alura.mspesquisa.dominio;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static br.com.alura.mspesquisa.dominio.ValidadorCpf.isValidCPF;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CpfComprador {

    private String cpf;

    public CpfComprador(String cpf) {
        if(!isValidCPF(cpf)) throw new IllegalArgumentException();
        this.cpf = cpf;
    }
}
