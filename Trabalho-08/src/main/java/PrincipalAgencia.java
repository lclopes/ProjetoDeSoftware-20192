import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import corejava.Console;
import excecao.AgenciaNaoEncontradaException;
import excecao.NumeroDeAgenciaInvalidoException;
import modelo.Agencia;
import modelo.Conta;
import modelo.Usuario;
import service.AgenciaAppService;
import service.LoginAppService;
import singleton.SingletonPerfis;

public class PrincipalAgencia {
    public static void main(String[] args) {
    	String nome;
    	int numero;
    	String endereco;
		Agencia umaAgencia;
	
		Usuario umUsuario;
	    
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");
	
		AgenciaAppService agenciaAppService = (AgenciaAppService) fabrica.getBean("agenciaAppService");

		LoginAppService loginAppService = (LoginAppService) fabrica.getBean("loginAppService");
		
		boolean continua = true;

		String conta = "";
		String senha = "";

		System.out.println('\n' + "==== BANCO LUCAS === Sistema de gerenciamento de ag�ncias === ");

		conta = Console.readLine("Login: ");
		senha = Console.readLine("Senha: ");

		umUsuario = loginAppService.loga(conta, senha);

		if (umUsuario != null) {
			System.out.println("Login realizado com sucesso!");
			continua = false;
		}

		while (continua) {
			System.out.println("Tente novamente.");

			conta = Console.readLine("Login: ");
			senha = Console.readLine("Senha: ");

			umUsuario = loginAppService.loga(conta, senha);

			if (umUsuario != null) {
				System.out.println("Login realizado com sucesso!");
				continua = false;
			}
		}

		SingletonPerfis singletonPerfis = SingletonPerfis.getSingletonPerfis();

		List<String> listaPerfis = new ArrayList<String>();

		umUsuario.getPerfis().forEach((p) -> {
			listaPerfis.add(p.getPerfil());
		});

		singletonPerfis.setPerfis(listaPerfis.toArray(new String[listaPerfis.size()]));

		continua = true;
		while (continua) {
			System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Cadastrar uma ag�ncia");
			System.out.println("2. Alterar dados de uma ag�ncia");
			System.out.println("3. Remover uma ag�ncia");
			System.out.println("4. Listar todas as ag�ncias");
			System.out.println("5. Listar as contas de uma ag�ncia");
			System.out.println("6. Sair");

			int opcao = Console.readInt('\n' + "Digite um n�mero entre 1 e 7:");

			switch (opcao) {
			case 1: {
				nome = Console.readLine('\n' + "Informe o nome da ag�ncia: ");
				numero = Console.readInt("Informe o numero da ag�ncia: ");
				endereco = Console.readLine("Informe o endere�o da ag�ncia: ");

				Agencia agencia = new Agencia(nome, numero, endereco);

				long n;
				try {
					n = agenciaAppService.inclui(agencia);
				} catch (NumeroDeAgenciaInvalidoException e) {
					System.out.println("N�mero da ag�ncia inv�lido!");
					break;
				}

				System.out.println('\n' + "Ag�ncia n�mero " + n + " inclu�do com sucesso!");

				break;
			}

			case 2: {
				int resposta = Console.readInt('\n' + "Digite o n�mero da ag�ncia que voc� deseja alterar: ");

				try {
					umaAgencia = agenciaAppService.recuperaUmaAgencia(resposta);
				} catch (AgenciaNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "N�mero = " + umaAgencia.getId() + "    Nome = " + umaAgencia.getNome()
						+ "    Endere�o = " + umaAgencia.getEndereco() + "      N�mero da ag�ncia = "
												+ umaAgencia.getNumero() );

				System.out.println('\n' + "O que voc� deseja alterar?");
				System.out.println('\n' + "1. Nome");
				System.out.println("2. N�mero");
				System.out.println("3. Endere�o");

				int opcaoAlteracao = Console.readInt('\n' + "Digite um n�mero de 1 a 3:");

				switch (opcaoAlteracao) {
				case 1:
					String novoNome = Console.readLine("Digite o novo nome: ");
					umaAgencia.setNome(novoNome);

					try {
						agenciaAppService.altera(umaAgencia);

						System.out.println('\n' + "Altera��o de nome efetuada com sucesso!");
					} catch (AgenciaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				case 2:
					int novoNumero = Console.readInt("Digite o novo n�mero: ");
					umaAgencia.setNumero(novoNumero);

					try {
						agenciaAppService.altera(umaAgencia);

						System.out.println('\n' + "Altera��o de n�mero efetuada " + "com sucesso!");
					} catch (AgenciaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				case 3:
					String novoEndereco = Console.readLine("Digite o novo endereco: ");
					umaAgencia.setEndereco(novoEndereco);

					try {
						agenciaAppService.altera(umaAgencia);

						System.out.println('\n' + "Altera��o de endere�o efetuada " + "com sucesso!");
					} catch (AgenciaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;
					
				default:
					System.out.println('\n' + "Op��o inv�lida!");
				}

				break;
			}

			case 3: {
				int resposta = Console.readInt('\n' + "Digite o n�mero da ag�ncia que voc� deseja remover: ");

				try {
					umaAgencia = agenciaAppService.recuperaUmaAgencia(resposta);
				} catch (AgenciaNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "N�mero = " + umaAgencia.getId() + "    Nome = " + umaAgencia.getNome()
				+ "    Endere�o = " + umaAgencia.getEndereco() + "      N�mero da ag�ncia = "
										+ umaAgencia.getNumero() );

				String resp = Console.readLine('\n' + "Confirma a remo��o da ag�ncia?");

				if (resp.equals("s")) {
					try {
						agenciaAppService.exclui(umaAgencia);
						System.out.println('\n' + "Ag�ncia removida com sucesso!");
					} catch (AgenciaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
				} else {
					System.out.println('\n' + "Ag�ncia n�o removida.");
				}

				break;
			}
			
			case 4: {
				List<Agencia> agencias = agenciaAppService.recuperaListaDeAgencias();
				for (Agencia agencia : agencias) {
					System.out.println('\n' + "      Agencia " + agencia.getId() + " -  N�mero = " + agencia.getNumero()
							+ "  Nome = " + agencia.getNome() + "  Endereco = " + agencia.getEndereco());
				}
				
				break;
			}

			case 5: {
				long id = Console.readInt('\n' + "Informe o n�mero da ag�ncia: ");

				try {
					umaAgencia = agenciaAppService.recuperaAgenciaEContas(id);
				} catch (AgenciaNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "      Agencia " + umaAgencia.getId() + " -  N�mero = " + umaAgencia.getNumero()
				+ "  Nome = " + umaAgencia.getNome() + "  Endereco = " + umaAgencia.getEndereco() + '\n');
				
				List<Conta> contas = umaAgencia.getContas();

				for (Conta c : contas) {
					System.out.println("�ndice = " + c.getId() +'\n'
							+ "Nome do titular = " + c.getCliente().getNome() + "  "
							+ "N�mero da ag�ncia = " + c.getAgencia().getNumero() + '\n' 
							+ "N�mero da conta: = " + c.getNumero() + "  "
							+ "Saldo = " + c.getSaldo() + '\n');
				}

				break;
			}
			
			case 6: {
				continua = false;
				break;
			}

			default:
				System.out.println('\n' + "Op��o inv�lida!");
			}
		}
	}
}
