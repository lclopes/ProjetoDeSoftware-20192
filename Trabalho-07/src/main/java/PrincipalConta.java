import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import corejava.Console;
import excecao.AgenciaNaoEncontradaException;
import excecao.ContaNaoEncontradaException;
import excecao.ClienteNaoEncontradoException;
import modelo.Agencia;
import modelo.Cliente;
import modelo.Conta;
import modelo.Usuario;
import service.AgenciaAppService;
import service.ClienteAppService;
import service.ContaAppService;
import service.LoginAppService;
import singleton.SingletonPerfis;

public class PrincipalConta {
	public static void main(String[] args) throws AgenciaNaoEncontradaException, ClienteNaoEncontradoException, ContaNaoEncontradaException {
    	int numero;
    	double saldo;
    	Agencia umaAgencia;
    	Cliente umCliente;
		Conta umaConta;
		    
		Usuario umUsuario;
		
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");
	
		ContaAppService contaAppService = (ContaAppService) fabrica.getBean("contaAppService");
		AgenciaAppService agenciaAppService = (AgenciaAppService) fabrica.getBean("agenciaAppService");
		ClienteAppService clienteAppService = (ClienteAppService) fabrica.getBean("clienteAppService");

		LoginAppService loginAppService = (LoginAppService) fabrica.getBean("loginAppService");
		
		boolean continua = true;

		String conta = "";
		String senha = "";

		System.out.println('\n' + "==== BANCO LUCAS === Sistema de gerenciamento de contas === ");

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
			System.out.println('\n' + "1. Cadastrar uma conta de um cliente e agência");
			System.out.println("2. Alterar o número ou o titular de uma conta");
			System.out.println("3. Remover uma conta");
			System.out.println("4. Listar todas as contas");
			System.out.println("5. Sair");

			int opcao = Console.readInt('\n' + "Digite um número entre 1 e 5:");

			switch (opcao) {
			case 1: {
				long id_agencia = Console.readInt('\n' + "Informe o índice da agência: ");
				long id_cliente = Console.readInt('\n' + "Informe o índice do cliente: ");
				
				try {
				    umaAgencia = agenciaAppService.recuperaUmaAgencia(id_agencia);
				}
				catch (AgenciaNaoEncontradaException e) {
				    System.out.println('\n' + e.getMessage());
				    break;
				}
				
				try {
				    umCliente = clienteAppService.recuperaUmCliente(id_cliente);
				}
				catch (ClienteNaoEncontradoException e) {
				    System.out.println('\n' + e.getMessage());
				    break;
				}
				
				/*
				if(contaAppService.verificaCliente(umCliente.getId())) {
					System.out.println('\n'+"Cliente já possui conta!");
					break;
				}*/	
								
				numero = Console.readInt('\n' + "Informe o número da conta: ");
				saldo = Console.readDouble('\n' + "Informe o saldo inicial da conta: ");

				umaConta = new Conta(numero, saldo, umaAgencia, umCliente);

				long n = contaAppService.inclui(umaConta);

				System.out.println('\n' + "Conta de índice " + n + " incluída com sucesso!");

				break;
			}

			case 2: {
				int resposta = Console.readInt('\n' + "Digite o número da conta que você deseja alterar: ");

				try {
					umaConta = contaAppService.recuperaUmaConta(resposta);
				} catch (ContaNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Índice = " + umaConta.getId() +'\n'
						+ "Nome do titular = " + umaConta.getCliente().getNome() + '\n'
						+ "Número da agência = " + umaConta.getAgencia().getNumero() + '\n' 
						+ "Número da conta: = " + umaConta.getNumero() + '\n'
						+ "Saldo = " + umaConta.getSaldo());

				System.out.println('\n' + "O que você deseja alterar?");
				System.out.println('\n' + "1. Número da conta");
				System.out.println("2. Titular");
				System.out.println("3. Saldo");
				
				int opcaoAlteracao = Console.readInt("Digite um número de 1 a 3:");
						
				switch (opcaoAlteracao) {
				case 1:
					int novoNumero= Console.readInt("Digite o novo número: ");
					umaConta.setNumero(novoNumero);

					try {
						contaAppService.altera(umaConta);
						System.out.println('\n' + "Alteração de número efetuada com sucesso!");
					} catch (ContaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
					break;

				case 2:
					long id_cliente = Console.readInt('\n' + "Informe o índice do novo cliente: ");
					Cliente novoCliente;
					
					try {
						novoCliente = clienteAppService.recuperaUmCliente(id_cliente);
					}
					catch (ClienteNaoEncontradoException e) {
					    System.out.println('\n' + e.getMessage());
					    break;
					}
					
					umaConta.setCliente(novoCliente);

					try {
						contaAppService.altera(umaConta);
						System.out.println('\n' + "Alteração de conta efetuada com sucesso!");
					} catch (ContaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
					break;
							
				case 3:
					double novoSaldo = Console.readDouble("Digite o valor a ser depositado na conta: "); 
					
					umaConta.setSaldo(novoSaldo);

					try {
						contaAppService.altera(umaConta);
						System.out.println('\n' + "Alteração de conta efetuada com sucesso!");
					} catch (ContaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
					break;	
				default:
					System.out.println('\n' + "Opção inválida!");
				}
	
				break;
			}

			case 3: {
				int resposta = Console.readInt('\n' + "Digite o número da conta que você deseja remover: ");

				try {
					umaConta = contaAppService.recuperaUmaConta(resposta);
				} catch (ContaNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Índice = " + umaConta.getId() + '\n'
				+ "Nome do titular = " + umaConta.getCliente().getNome() + '\n'
				+ "Número da agência = " + umaConta.getAgencia().getNumero() + '\n' 
				+ "Número da conta = " + umaConta.getNumero() + '\n'
				+ "Saldo = " + umaConta.getSaldo());

				String resp = Console.readLine('\n' + "Confirma a remoção da conta?");

				if (resp.equals("s")) {
					try {
						contaAppService.exclui(umaConta);
						System.out.println('\n' + "Conta removida com sucesso!");
					} catch (AgenciaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
				} else {
					System.out.println('\n' + "Conta não removida.");
				}

				break;
			}
			
			case 4: {
				List<Conta> contas = contaAppService.recuperaListaDeContas();
				for (Conta c : contas) {
					System.out.println('\n' + "Índice = " + c.getId() + '\n'
					+ "Nome do titular = " + c.getCliente().getNome() + '\n'
					+ "Número da agência = " + c.getAgencia().getNumero() + '\n' 
					+ "Número da conta = " + c.getNumero() + '\n'
					+ "Saldo = " + c.getSaldo());
				}
				
				break;
			}
			case 5: {
				continua = false;
				break;
			}

			default:
				System.out.println('\n' + "Opção inválida!");
			}
}}}
