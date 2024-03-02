package br.com.alura.mspesquisa.dominio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioDeVoosSelecionados extends JpaRepository<VooSelecionado, Long> {
}
