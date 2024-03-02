package br.com.alura.mscompanhiasaereas.dominio;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.doRioParaBeloHorizonteAmanha;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RepositorioDeVoosTest {

    @Autowired
    private RepositorioDeVoos repositorioDeVoos;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Voo voo;

    @BeforeEach
    void setUp() {
        voo = doRioParaBeloHorizonteAmanha();
        repositorioDeVoos.save(voo);
    }

    @AfterEach
    void tearDown(){
        mongoTemplate.remove(voo);
    }

    @Test
    void cadastrarUmVoo() {
        mongoTemplate.findAll(Voo.class);

        assertThat(mongoTemplate.findAll(Voo.class)).contains(voo);
    }

    @Test
    void removerVoo(){
        repositorioDeVoos.deleteById(voo.getId());

        assertThat(mongoTemplate.findAll(Voo.class)).doesNotContain(voo);
    }
}