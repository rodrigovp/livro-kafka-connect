package br.com.alura.mscompanhiasaereas.relatorio;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.origemCongonhasAmanha;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RepositorioDeOrigensParaRelatorioTest {

    @Autowired
    private RepositorioDeOrigensParaRelatorio repositorioDeOrigensParaRelatorio;

    @Autowired
    private MongoTemplate mongoTemplate;

    private OrigemParaRelatorio origemParaRelatorio;

    @BeforeEach
    void setUp(){
        origemParaRelatorio = new OrigemParaRelatorio(origemCongonhasAmanha());
        repositorioDeOrigensParaRelatorio.save(origemParaRelatorio);
    }

    @AfterEach
    void tearDown(){
        mongoTemplate.remove(origemParaRelatorio);
    }

    @Test
    void cadastrarUmaOrigemParaRelatorio(){
        assertThat(mongoTemplate.findAll(OrigemParaRelatorio.class)).contains(origemParaRelatorio);
    }
}