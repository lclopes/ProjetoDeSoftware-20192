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
			System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Cadastrar uma conta de um cliente e ag�ncia");
			System.out.println("2. Alterar o n�mero ou o titular de uma conta");
			System.out.println("3. Remover uma conta");
			System.out.println("4. Listar todas as contas");
			System.out.println("5. Sair");

			int opcao = Console.readInt('\n' + "Digite um n�mero entre 1 e 5:");

			switch (opcao) {
			case 1: {
				long id_agencia = Console.readInt('\n' + "Informe o �ndice da ag�ncia: ");
				long id_cliente = Console.readInt('\n' + "Informe o �ndice do cliente: ");
				
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
					System.out.println('\n'+"Cliente j� possui conta!");
					break;
				}*/	
								
				numero = Console.readInt('\n' + "Informe o n�mero da conta: ");
				saldo = Console.readDouble('\n' + "Informe o saldo inicial da conta: ");

				umaConta = new Conta(numero, saldo, umaAgencia, umCliente);

				long n = contaAppService.inclui(umaConta);

				System.out.println('\n' + "Conta de �ndice " + n + " inclu�da com sucesso!");

				break;
			}

			case 2: {
				int resposta = Console.readInt('\n' + "Digite o n�mero da conta que voc� deseja alterar: ");

				try {
					umaConta = contaAppService.recuperaUmaConta(resposta);
				} catch (ContaNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "�ndice = " + umaConta.getId() +'\n'
						+ "Nome do titular = " + umaConta.getCliente().getNome() + '\n'
						+ "N�mero da ag�ncia = " + umaConta.getAgencia().getNumero() + '\n' 
						+ "N�mero da conta: = " + umaConta.getNumero() + '\n'
						+ "Saldo = " + umaConta.getSaldo());

				System.out.println('\n' + "O que voc� deseja alterar?");
				System.out.println('\n' + "1. N�mero da conta");
				System.out.println("2. Titular");
				System.out.println("3. Saldo");
				
				int opcaoAlteracao = Console.readInt("Digite um n�mero de 1 a 3:");
						
				switch (opcaoAlteracao) {
				case 1:
					int novoNumero= Console.readInt("Digite o novo n�mero: ");
					umaConta.setNumero(novoNumero);

					try {
						contaAppService.altera(umaConta);
						System.out.println('\n' + "Altera��o de n�mero efetuada com sucesso!");
					} catch (ContaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
					break;

				case 2:
					long id_cliente = Console.readInt('\n' + "Informe o �ndice do novo cliente: ");
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
						System.out.println('\n' + "Altera��o de conta efetuada com sucesso!");
					} catch (ContaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
					break;
							
				case 3:
					double novoSaldo = Console.readDouble("Digite o valor a ser depositado na conta: "); 
					
					umaConta.setSaldo(novoSaldo);

					try {
						contaAppService.altera(umaConta);
						System.out.println('\n' + "Altera��o de conta efetuada com sucesso!");
					} catch (ContaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
					break;	
				default:
					System.out.println('\n' + "Op��o inv�lida!");
				}
	
				break;
			}

			case 3: {
				int resposta = Console.readInt('\n' + "Digite o n�mero da conta que voc� deseja remover: ");

				try {
					umaConta = contaAppService.recuperaUmaConta(resposta);
				} catch (ContaNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "�ndice = " + umaConta.getId() + '\n'
				+ "Nome do titular = " + umaConta.getCliente().getNome() + '\n'
				+ "N�mero da ag�ncia = " + umaConta.getAgencia().getNumero() + '\n' 
				+ "N�mero da conta = " + umaConta.getNumero() + '\n'
				+ "Saldo = " + umaConta.getSaldo());

				String resp = Console.readLine('\n' + "Confirma a remo��o da conta?");

				if (resp.equals("s")) {
					try {
						contaAppService.exclui(umaConta);
						System.out.println('\n' + "Conta removida com sucesso!");
					} catch (AgenciaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
				} else {
					System.out.println('\n' + "Conta n�o removida.");
				}

				break;
			}
			
			case 4: {
				List<Conta> contas = contaAppService.recuperaListaDeContas();
				for (Conta c : contas) {
					System.out.println('\n' + "�ndice = " + c.getId() + '\n'
					+ "Nome do titular = " + c.getCliente().getNome() + '\n'
					+ "N�mero da ag�ncia = " + c.getAgencia().getNumero() + '\n' 
					+ "N�mero da conta = " + c.getNumero() + '\n'
					+ "Saldo = " + c.getSaldo());
				}
				
				break;
			}
			case 5: {
				continua = false;
				break;
			}

			default:
				System.out.println('\n' + "Op��o inv�lida!");
			}
}}}
