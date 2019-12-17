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

		System.out.println('\n' + "==== BANCO LUCAS === Sistema de gerenciamento de agências === ");

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
			System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar uma agência");
			System.out.println("2. Alterar dados de uma agência");
			System.out.println("3. Remover uma agência");
			System.out.println("4. Listar todas as agências");
			System.out.println("5. Listar as contas de uma agência");
			System.out.println("6. Sair");

			int opcao = Console.readInt('\n' + "Digite um número entre 1 e 7:");

			switch (opcao) {
			case 1: {
				nome = Console.readLine('\n' + "Informe o nome da agência: ");
				numero = Console.readInt("Informe o numero da agência: ");
				endereco = Console.readLine("Informe o endereço da agência: ");

				Agencia agencia = new Agencia(nome, numero, endereco);

				long n;
				try {
					n = agenciaAppService.inclui(agencia);
				} catch (NumeroDeAgenciaInvalidoException e) {
					System.out.println("Número da agência inválido!");
					break;
				}

				System.out.println('\n' + "Agência número " + n + " incluído com sucesso!");

				break;
			}

			case 2: {
				int resposta = Console.readInt('\n' + "Digite o número da agência que você deseja alterar: ");

				try {
					umaAgencia = agenciaAppService.recuperaUmaAgencia(resposta);
				} catch (AgenciaNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Número = " + umaAgencia.getId() + "    Nome = " + umaAgencia.getNome()
						+ "    Endereço = " + umaAgencia.getEndereco() + "      Número da agência = "
												+ umaAgencia.getNumero() );

				System.out.println('\n' + "O que você deseja alterar?");
				System.out.println('\n' + "1. Nome");
				System.out.println("2. Número");
				System.out.println("3. Endereço");

				int opcaoAlteracao = Console.readInt('\n' + "Digite um número de 1 a 3:");

				switch (opcaoAlteracao) {
				case 1:
					String novoNome = Console.readLine("Digite o novo nome: ");
					umaAgencia.setNome(novoNome);

					try {
						agenciaAppService.altera(umaAgencia);

						System.out.println('\n' + "Alteração de nome efetuada com sucesso!");
					} catch (AgenciaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				case 2:
					int novoNumero = Console.readInt("Digite o novo número: ");
					umaAgencia.setNumero(novoNumero);

					try {
						agenciaAppService.altera(umaAgencia);

						System.out.println('\n' + "Alteração de número efetuada " + "com sucesso!");
					} catch (AgenciaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				case 3:
					String novoEndereco = Console.readLine("Digite o novo endereco: ");
					umaAgencia.setEndereco(novoEndereco);

					try {
						agenciaAppService.altera(umaAgencia);

						System.out.println('\n' + "Alteração de endereço efetuada " + "com sucesso!");
					} catch (AgenciaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;
					
				default:
					System.out.println('\n' + "Opção inválida!");
				}

				break;
			}

			case 3: {
				int resposta = Console.readInt('\n' + "Digite o número da agência que você deseja remover: ");

				try {
					umaAgencia = agenciaAppService.recuperaUmaAgencia(resposta);
				} catch (AgenciaNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Número = " + umaAgencia.getId() + "    Nome = " + umaAgencia.getNome()
				+ "    Endereço = " + umaAgencia.getEndereco() + "      Número da agência = "
										+ umaAgencia.getNumero() );

				String resp = Console.readLine('\n' + "Confirma a remoção da agência?");

				if (resp.equals("s")) {
					try {
						agenciaAppService.exclui(umaAgencia);
						System.out.println('\n' + "Agência removida com sucesso!");
					} catch (AgenciaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
				} else {
					System.out.println('\n' + "Agência não removida.");
				}

				break;
			}
			
			case 4: {
				List<Agencia> agencias = agenciaAppService.recuperaListaDeAgencias();
				for (Agencia agencia : agencias) {
					System.out.println('\n' + "      Agencia " + agencia.getId() + " -  Número = " + agencia.getNumero()
							+ "  Nome = " + agencia.getNome() + "  Endereco = " + agencia.getEndereco());
				}
				
				break;
			}

			case 5: {
				long id = Console.readInt('\n' + "Informe o número da agência: ");

				try {
					umaAgencia = agenciaAppService.recuperaAgenciaEContas(id);
				} catch (AgenciaNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "      Agencia " + umaAgencia.getId() + " -  Número = " + umaAgencia.getNumero()
				+ "  Nome = " + umaAgencia.getNome() + "  Endereco = " + umaAgencia.getEndereco() + '\n');
				
				List<Conta> contas = umaAgencia.getContas();

				for (Conta c : contas) {
					System.out.println("Índice = " + c.getId() +'\n'
							+ "Nome do titular = " + c.getCliente().getNome() + "  "
							+ "Número da agência = " + c.getAgencia().getNumero() + '\n' 
							+ "Número da conta: = " + c.getNumero() + "  "
							+ "Saldo = " + c.getSaldo() + '\n');
				}

				break;
			}
			
			case 6: {
				continua = false;
				break;
			}

			default:
				System.out.println('\n' + "Opção inválida!");
			}
		}
	}
}
