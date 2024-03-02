package br.com.alura.msselecaohospedagens.dominio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioDeHospedagensSelecionadas extends JpaRepository<HospedagemSelecionada, Long> {
}
