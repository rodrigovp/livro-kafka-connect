package br.com.alura.msselecaohospedagens;

import br.com.alura.msselecaohospedagens.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@ConditionalOnProperty(
        prefix = "carga-inicial-banco",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
class CargaInicialDoBanco implements ApplicationRunner {

	@Autowired
	private RepositorioDeHospedagens repositorioDeHospedagens;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {    	
    	List<Hospedagem> hospedagens = List.of(
    			new Hospedagem("59520141000188", "Pousada do Javeiro", TipoHospedagem.POUSADA, Aeroporto.BSB, 
    					new Diaria(new BigDecimal("200.00"), new BigDecimal("100.00"))),
    			new Hospedagem("54657796000189", "Hostel dos Camaradas", TipoHospedagem.HOSTEL, Aeroporto.BSB, 
    					new Diaria(new BigDecimal("201.00"), new BigDecimal("101.00"))),
    			new Hospedagem("73122746000141", "Hotel Imperador", TipoHospedagem.HOTEL, Aeroporto.BSB, 
    					new Diaria(new BigDecimal("202.00"), new BigDecimal("102.00"))),
    			new Hospedagem("62323559000100", "Pousada Chique", TipoHospedagem.POUSADA, Aeroporto.CGH, 
    					new Diaria(new BigDecimal("203.00"), new BigDecimal("103.00"))),
    			new Hospedagem("81002473000120", "Hostel Internacional", TipoHospedagem.HOSTEL, Aeroporto.CGH, 
    					new Diaria(new BigDecimal("200.00"), new BigDecimal("100.00"))),
    			new Hospedagem("85886550000102", "Hotel Presidente", TipoHospedagem.HOTEL, Aeroporto.CGH, 
    					new Diaria(new BigDecimal("201.00"), new BigDecimal("101.00"))),
    			new Hospedagem("35460410000108", "Pousada da Preguiça", TipoHospedagem.POUSADA, Aeroporto.GRU, 
    					new Diaria(new BigDecimal("202.00"), new BigDecimal("102.00"))),
    			new Hospedagem("83567711000170", "Hostel Azul", TipoHospedagem.HOSTEL, Aeroporto.GRU, 
    					new Diaria(new BigDecimal("203.00"), new BigDecimal("103.00"))),
    			new Hospedagem("56438482000110", "Hotel Java Inn", TipoHospedagem.HOTEL, Aeroporto.GRU, 
    					new Diaria(new BigDecimal("200.00"), new BigDecimal("100.00"))),
    			new Hospedagem("72379643000107", "Pousada e Raiz", TipoHospedagem.POUSADA, Aeroporto.SDU, 
    					new Diaria(new BigDecimal("201.00"), new BigDecimal("101.00"))),
    			new Hospedagem("42477223000194", "Hostel Amizade", TipoHospedagem.HOSTEL, Aeroporto.SDU, 
    					new Diaria(new BigDecimal("202.00"), new BigDecimal("102.00"))),
    			new Hospedagem("61331527000193", "Hotel San Nicolas", TipoHospedagem.HOTEL, Aeroporto.SDU, 
    					new Diaria(new BigDecimal("203.00"), new BigDecimal("103.00")))
    			);
    	
    	hospedagens.forEach(hospedagem ->
    		repositorioDeHospedagens.findByCnpj(hospedagem.getCnpj())
    			.ifPresentOrElse(
    				hospedagemExistente -> System.out.println("Hospedagem já existente: " + hospedagemExistente), 
    				() -> repositorioDeHospedagens.save(hospedagem))
    	); 
    }
}
