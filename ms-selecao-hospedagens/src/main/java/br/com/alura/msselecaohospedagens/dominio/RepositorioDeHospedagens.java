package br.com.alura.msselecaohospedagens.dominio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RepositorioDeHospedagens extends JpaRepository<Hospedagem, Long> {

	Set<Hospedagem> findByAeroportoMaisProximo(Aeroporto aeroportoMaisProximo);

	Optional<Hospedagem> findByCnpj(String cnpj);
}
