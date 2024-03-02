package br.com.alura.mscompanhiasaereas.service;

import br.com.alura.mscompanhiasaereas.dominio.RepositorioDeVoos;
import br.com.alura.mscompanhiasaereas.dominio.Voo;
import br.com.alura.mscompanhiasaereas.relatorio.OrigemParaRelatorio;
import br.com.alura.mscompanhiasaereas.relatorio.RepositorioDeOrigensParaRelatorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.MongoTransactionException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

import static br.com.alura.mscompanhiasaereas.dominio.ObjetosParaTestes.doRioParaBeloHorizonteAmanha;
import static br.com.alura.mscompanhiasaereas.relatorio.OrigemParaRelatorio.origemDoVoo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class VooServiceIntegrationTest {

    @Autowired
    private VooService vooService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @SpyBean
    private RepositorioDeVoos repositorioDeVoos;

    @SpyBean
    private RepositorioDeOrigensParaRelatorio repositorioDeOrigensParaRelatorio;

    private Voo voo;
    private OrigemParaRelatorio origemParaRelatorio;

    @BeforeEach
    void setUp(){
        voo = doRioParaBeloHorizonteAmanha();
        origemParaRelatorio = origemDoVoo(voo);
    }

    @Test
    @Transactional
    void cadastrarNovoVoo(){
        vooService.cadastrarNovo(voo);

        assertThat(mongoTemplate.findAll(Voo.class)).contains(voo);
        assertThat(mongoTemplate.findAll(OrigemParaRelatorio.class)).contains(origemParaRelatorio);
    }

    @Test
    void erroNoCadastroDeOrigensParaRelatorioImpedeOCadastroDoNovoVoo(){
        doThrow(MongoTransactionException.class).when(repositorioDeOrigensParaRelatorio).save(origemParaRelatorio);

        try{
            vooService.cadastrarNovo(voo);
            fail("Deveria ter lançado exception!");
        } catch(MongoTransactionException e){
            assertThat(mongoTemplate.findAll(Voo.class)).doesNotContain(voo);
            assertThat(mongoTemplate.findAll(OrigemParaRelatorio.class)).doesNotContain(origemParaRelatorio);
        }
    }

    @Test
    void erroNoCadastroDoNovoVooImpedeOCadastroDeOrigemParaRelatorio(){
        doThrow(MongoTransactionException.class).when(repositorioDeVoos).save(voo);
        try{
            vooService.cadastrarNovo(voo);
            fail("Deveria ter lançado exception!");
        } catch(MongoTransactionException e){
            assertThat(mongoTemplate.findAll(Voo.class)).doesNotContain(voo);
            assertThat(mongoTemplate.findAll(OrigemParaRelatorio.class)).doesNotContain(origemParaRelatorio);
        }
    }
}
