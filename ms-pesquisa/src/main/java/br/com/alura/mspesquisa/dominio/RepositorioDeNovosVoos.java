package br.com.alura.mspesquisa.dominio;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface RepositorioDeNovosVoos extends JpaRepository<Voo, Long> {

    @Transactional
    void deleteByIdCompanhiaAerea(IdentificadorCompanhiaAerea identificadorCompanhiaAerea);

    Optional<Voo> findByIdCompanhiaAerea(IdentificadorCompanhiaAerea identificadorCompanhiaAerea);

    Set<Voo> findByOrigemAeroportoAndOrigemPartidaBetweenAndDestinoAeroporto(
            Aeroporto aeroportoOrigem,
            LocalDateTime partidaInicial,
            LocalDateTime partidaFinal,
            Aeroporto aeroportoDestino);
}
