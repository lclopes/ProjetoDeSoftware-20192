import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import corejava.Console;
import excecao.ClienteNaoEncontradoException;
import modelo.Cliente;

import service.ClienteAppService;
import util.Util;

public class PrincipalCliente {
    public static void main(String[] args) {
    	String nome;
    	String endereco;
    	String dataNasc;
    	String cpf;
    	String rg;
    	String dataCadastro;
		Cliente umCliente;
	
	    
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");
	
		ClienteAppService clienteAppService = (ClienteAppService) fabrica.getBean("clienteAppService");

		boolean continua = true;
		while (continua) {
			System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um cliente");
			System.out.println("2. Alterar dados de um cliente");
			System.out.println("3. Remover um cliente");
			System.out.println("4. Listar todas os clientes");
			System.out.println("5. Sair");

			int opcao = Console.readInt('\n' + "Digite um número entre 1 e 5:");

			switch (opcao) {
			case 1: {
				nome = Console.readLine('\n' + "Informe o nome do cliente: ");
				endereco = Console.readLine("Informe o endereço do cliente: ");
				dataNasc = Console.readLine("Informe a data de nascimento do cliente: ");
				cpf = Console.readLine("Informe o CPF do cliente: ");
				rg = Console.readLine("Informe o RG do cliente: ");
				dataCadastro = Console.readLine("Informe a data de cadastro do cliente: ");

				Cliente cliente = new Cliente(nome, endereco, Util.strToDate(dataNasc),
						cpf, rg, Util.strToDate(dataCadastro));

				long n = clienteAppService.inclui(cliente);

				System.out.println('\n' + "Cliente número " + n + " incluído com sucesso!");

				break;
			}

			case 2: {
				int resposta = Console.readInt('\n' + "Digite o número do índice do cliente que você deseja alterar: ");

				try {
					umCliente = clienteAppService.recuperaUmCliente(resposta);
				} catch (ClienteNaoEncontradoException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' 
						+ "Número = " + umCliente.getId() + '\n'
						+ "Nome = " + umCliente.getNome() + '\n'
						+ "Endereço = " + umCliente.getEndereco() + '\n'
						+ "Data de Nascimento = " + umCliente.getDataNasc().toString() + '\n'
						+ "CPF = " + umCliente.getCpf() + '\n'
						+ "RG = " + umCliente.getRg() + '\n'
						+ "Data de Cadastro = " + umCliente.getDataCadastro().toString() + '\n');

				System.out.println('\n' + "O que você deseja alterar?");
				System.out.println('\n' + "1. Nome");
				System.out.println("2. Endereço");
				System.out.println("3. Data de Nascimento");
				System.out.println("4. CPF");
				System.out.println("5. RG");
				

				int opcaoAlteracao = Console.readInt('\n' + "Digite um número de 1 a 6:");

				switch (opcaoAlteracao) {
				case 1:
					String novoNome = Console.readLine("Digite o novo nome: ");
					umCliente.setNome(novoNome);

					try {
						clienteAppService.altera(umCliente);

						System.out.println('\n' + "Alteração de nome efetuada com sucesso!");
					} catch (ClienteNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				case 2:
					String novoEndereco = Console.readLine("Digite o novo endereco: ");
					umCliente.setEndereco(novoEndereco);

					try {
						clienteAppService.altera(umCliente);

						System.out.println('\n' + "Alteração de endereço efetuada " + "com sucesso!");
					} catch (ClienteNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				case 3:
					String novaDataNasc = Console.readLine("Digite a nova data de nascimento: ");
					umCliente.setDataNasc(Util.strToDate(novaDataNasc));

					try {
						clienteAppService.altera(umCliente);

						System.out.println('\n' + "Alteração de endereço efetuada " + "com sucesso!");
					} catch (ClienteNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;
					
				case 4:
					String novoCPF = Console.readLine("Digite o novo CPF: ");
					umCliente.setCpf(novoCPF);

					try {
						clienteAppService.altera(umCliente);

						System.out.println('\n' + "Alteração de CPF efetuada com sucesso!");
					} catch (ClienteNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				case 5:
					String novoRG = Console.readLine("Digite o novo RG: ");
					umCliente.setNome(novoRG);

					try {
						clienteAppService.altera(umCliente);

						System.out.println('\n' + "Alteração de RG efetuada " + "com sucesso!");
					} catch (ClienteNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;
					
				default:
					System.out.println('\n' + "Opção inválida!");
				}

				break;
			}

			case 3: {
				int resposta = Console.readInt('\n' + "Digite o número do índice do cliente"
						+ " que você deseja remover: ");

				try {
					umCliente = clienteAppService.recuperaUmCliente(resposta);
				} catch (ClienteNaoEncontradoException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' 
						+ "Número = " + umCliente.getId() + '\n'
						+ "Nome = " + umCliente.getNome() + '\n'
						+ "Endereço = " + umCliente.getEndereco() + '\n'
						+ "Data de Nascimento = " + umCliente.getDataNasc().toString() + '\n'
						+ "CPF = " + umCliente.getCpf() + '\n'
						+ "RG = " + umCliente.getRg() + '\n'
						+ "Data de Cadastro = " + umCliente.getDataCadastro().toString() + '\n');

				String resp = Console.readLine('\n' + "Confirma a remoção do cliente?");

				if (resp.equals("s")) {
					try {
						clienteAppService.exclui(umCliente);
						System.out.println('\n' + "Cliente removido com sucesso!");
					} catch (ClienteNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
					}
				} else {
					System.out.println('\n' + "Cliente não removido.");
				}

				break;
			}
			
			case 4: {
				List<Cliente> clientes = clienteAppService.recuperaListaDeClientes();
				for (Cliente cliente : clientes) {
					System.out.println('\n' 
							+ "Número = " + cliente.getId() + '\n'
							+ "Nome = " + cliente.getNome() + '\n'
							+ "Endereço = " + cliente.getEndereco() + '\n'
							+ "Data de Nascimento = " + cliente.getDataNasc().toString() + '\n'
							+ "CPF = " + cliente.getCpf() + '\n'
							+ "RG = " + cliente.getRg() + '\n'
							+ "Data de Cadastro = " + cliente.getDataCadastro().toString() + '\n');
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
		}
	}
}
