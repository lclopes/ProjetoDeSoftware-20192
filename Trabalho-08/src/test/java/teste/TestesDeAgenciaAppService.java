package teste;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import excecao.NumeroDeAgenciaInvalidoException;
import excecao.AgenciaComNumeroRepetidoException;
import modelo.Agencia;
import modelo.Usuario;
import service.AgenciaAppService;
import service.LoginAppService;
import singleton.SingletonPerfis;

@ContextConfiguration(locations = "/beans-jpa.xml")
public class TestesDeAgenciaAppService extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private AgenciaAppService agenciaAppService;
	
	@Autowired
	private LoginAppService loginAppService;
	
	private Usuario umUsuario;
	
//  @Test
//  @Timed(millis = 1000)  // Para garantir que um teste termina em um determinado período de tempo
//  @Repeat(5)             // O tempo inclui as repetições e as tarefas de inicialização e de limpesa.
	
	@Test(expected = NumeroDeAgenciaInvalidoException.class)
	public void incluiAgencia1() throws Throwable {
		System.out.println(">>>>>>>>>>>>> Começou a execução do teste 1");

		String conta = "";
		String senha = "";

		conta = "lucas";
		senha = "senha";
		
		umUsuario = loginAppService.loga(conta, senha);

		if (umUsuario != null) {
			System.out.println("Login realizado com sucesso!");
		}

		SingletonPerfis singletonPerfis = SingletonPerfis.getSingletonPerfis();

		List<String> listaPerfis = new ArrayList<String>();

		umUsuario.getPerfis().forEach((p) -> {
			listaPerfis.add(p.getPerfil());
		});

		singletonPerfis.setPerfis(listaPerfis.toArray(new String[listaPerfis.size()]));
		
		Agencia agencia = new Agencia("Agencia X", 14854,"Rua Y");
		agenciaAppService.inclui(agencia);
		
	}
	
	@Test(expected = AgenciaComNumeroRepetidoException.class)
	public void incluiAgencia2() throws Throwable {
		System.out.println(">>>>>>>>>>>>> Começou a execução do teste 2");

		String conta = "";
		String senha = "";

		conta = "lucas";
		senha = "senha";
		
		umUsuario = loginAppService.loga(conta, senha);

		if (umUsuario != null) {
			System.out.println("Login realizado com sucesso!");
		}

		SingletonPerfis singletonPerfis = SingletonPerfis.getSingletonPerfis();

		List<String> listaPerfis = new ArrayList<String>();

		umUsuario.getPerfis().forEach((p) -> {
			listaPerfis.add(p.getPerfil());
		});

		singletonPerfis.setPerfis(listaPerfis.toArray(new String[listaPerfis.size()]));
		
		Agencia agencia = new Agencia("Agencia X", 2840,"Rua Y");
		Agencia agencia2 = new Agencia("Agencia Y", 2840,"Rua Z");
		agenciaAppService.inclui(agencia);
		try{
			agenciaAppService.inclui(agencia2);
		} catch (AgenciaComNumeroRepetidoException e) {
			fail("Não foi encontrado um produto cadastrado.");
		}
		
	}
	
}
