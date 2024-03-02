package br.com.alura.mspesquisa;

import br.com.alura.mspesquisa.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static br.com.alura.mspesquisa.dominio.DateUtils.localDateTimeOf;

@Component
@ConditionalOnProperty(
        prefix = "carga-inicial-banco",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
class CargaInicialDoBanco implements ApplicationRunner {

    @Autowired
    private RepositorioDeNovosVoos repositorioDeNovosVoos;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Voo> voos = List.of(
                new Voo(new IdentificadorCompanhiaAerea("65136e3571e71b6bcc8b3020"),
                        new Origem(Aeroporto.GRU, localDateTimeOf("2024-08-14T18:38:39")),
                        new Destino(Aeroporto.SDU, localDateTimeOf("2024-08-14T20:38:39")),
                        CompanhiaAerea.AVIANCA,
                        new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00"))
                )
                , new Voo(new IdentificadorCompanhiaAerea("65136e3571e71b6bcc8b3021"),
                        new Origem(Aeroporto.CGH, localDateTimeOf("2024-08-15T18:38:39")),
                        new Destino(Aeroporto.SDU, localDateTimeOf("2024-08-15T20:38:39")),
                        CompanhiaAerea.GOL,
                        new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00"))
                )
                , new Voo(new IdentificadorCompanhiaAerea("65136e3571e71b6bcc8b3022"),
                        new Origem(Aeroporto.CGH, localDateTimeOf("2024-08-16T18:38:39")),
                        new Destino(Aeroporto.SDU, localDateTimeOf("2024-08-16T20:38:39")),
                        CompanhiaAerea.AZUL,
                        new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00"))
                )
                , new Voo(new IdentificadorCompanhiaAerea("65136e3571e71b6bcc8b3023"),
                        new Origem(Aeroporto.BSB, localDateTimeOf("2024-08-17T18:38:39")),
                        new Destino(Aeroporto.SDU, localDateTimeOf("2024-08-17T20:38:39")),
                        CompanhiaAerea.LATAM,
                        new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00"))
                )
                , new Voo(new IdentificadorCompanhiaAerea("65136e3571e71b6bcc8b3024"),
                        new Origem(Aeroporto.SDU, localDateTimeOf("2024-08-18T18:38:39")),
                        new Destino(Aeroporto.BSB, localDateTimeOf("2024-08-18T20:38:39")),
                        CompanhiaAerea.AZUL,
                        new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00"))
                )
                , new Voo(new IdentificadorCompanhiaAerea("65136e3571e71b6bcc8b3025"),
                        new Origem(Aeroporto.CGH, localDateTimeOf("2024-08-19T18:38:39")),
                        new Destino(Aeroporto.BSB, localDateTimeOf("2024-08-19T20:38:39")),
                        CompanhiaAerea.LATAM,
                        new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00"))
                )
                , new Voo(new IdentificadorCompanhiaAerea("65136e3571e71b6bcc8b3026"),
                        new Origem(Aeroporto.BSB, localDateTimeOf("2024-08-20T18:38:39")),
                        new Destino(Aeroporto.SDU, localDateTimeOf("2024-08-20T20:38:39")),
                        CompanhiaAerea.GOL,
                        new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00"))
                )
                , new Voo(new IdentificadorCompanhiaAerea("65136e3571e71b6bcc8b3027"),
                        new Origem(Aeroporto.CGH, localDateTimeOf("2024-08-21T18:38:39")),
                        new Destino(Aeroporto.SDU, localDateTimeOf("2024-08-21T20:38:39")),
                        CompanhiaAerea.AZUL,
                        new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00"))
                )
                , new Voo(new IdentificadorCompanhiaAerea("65136e3571e71b6bcc8b3028"),
                        new Origem(Aeroporto.CGH, localDateTimeOf("2024-08-21T10:38:39")),
                        new Destino(Aeroporto.SDU, localDateTimeOf("2024-08-21T12:38:39")),
                        CompanhiaAerea.GOL,
                        new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00"))
                )
                , new Voo(new IdentificadorCompanhiaAerea("65136e3571e71b6bcc8b3029"),
                        new Origem(Aeroporto.GRU, localDateTimeOf("2024-08-23T18:38:39")),
                        new Destino(Aeroporto.SDU, localDateTimeOf("2024-08-23T20:38:39")),
                        CompanhiaAerea.GOL,
                        new Preco(new BigDecimal("1000.00"), new BigDecimal("500.00"))
                )

        );
        voos.forEach(voo -> {
            repositorioDeNovosVoos.findByIdCompanhiaAerea(voo.getIdCompanhiaAerea()).ifPresentOrElse(
                    vooExistente -> System.out.println("Voo já existe: " + vooExistente),
                    () -> repositorioDeNovosVoos.save(voo)
            );
        });

    };

}
