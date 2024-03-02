package br.com.alura.mspesquisa.dominio;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Aeroporto {

        CGH("Congonhas"),
        GRU("Cumbica"),
        BSB("Confins"),
        SDU("Santos Dumont");

        private String nome;
}
