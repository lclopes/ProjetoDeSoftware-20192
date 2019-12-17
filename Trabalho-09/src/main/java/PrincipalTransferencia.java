import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import corejava.Console;
import excecao.AgenciaNaoEncontradaException;
import excecao.ContaNaoEncontradaException;
import excecao.ClienteNaoEncontradoException;
import excecao.SaldoInsuficienteException;
import excecao.TransferenciaNaoEncontradaException;
import modelo.Conta;
import modelo.Transferencia;
import modelo.Usuario;
import service.ContaAppService;
import service.LoginAppService;
import service.TransferenciaAppService;
import singleton.SingletonPerfis;

public class PrincipalTransferencia {
	public static void main(String[] args) throws AgenciaNaoEncontradaException, 
	ClienteNaoEncontradoException, TransferenciaNaoEncontradaException, SaldoInsuficienteException, 
	ContaNaoEncontradaException {
		double valor;
		Conta umaContaOrigem;
		Conta umaContaDestino;
		Transferencia umaTransferencia;
		
		Usuario umUsuario;
		
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");
	
		ContaAppService contaAppService = (ContaAppService) fabrica.getBean("contaAppService");
		TransferenciaAppService transferenciaAppService = (TransferenciaAppService) fabrica.getBean("transferenciaAppService");

		LoginAppService loginAppService = (LoginAppService) fabrica.getBean("loginAppService");
		
		boolean continua = true;

		String conta = "";
		String senha = "";

		System.out.println('\n' + "==== BANCO LUCAS === Sistema de gerenciamento de transfer�ncias === ");

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
			System.out.println('\n' + "1. Inserir uma nova transfer�ncia entre contas");
			System.out.println("2. Remover uma transfer�ncia do registro");
			System.out.println("3. Listar todas as transfer�ncias");
			System.out.println("4. Listar todas as transfer�ncias feitas e recebidas por uma conta");
			System.out.println("5. Sair");

			int opcao = Console.readInt('\n' + "Digite um n�mero entre 1 e 5:");

			switch (opcao) {
			case 1: {
				long id_conta_origem = Console.readInt('\n' + "Informe o �ndice da conta de origem: ");
				long id_conta_destino = Console.readInt('\n' + "Informe o �ndice da conta de destino: ");
				
				try {
				    umaContaOrigem = contaAppService.recuperaUmaConta(id_conta_origem);
				}
				catch (ContaNaoEncontradaException e) {
				    System.out.println('\n' + e.getMessage());
				    break;
				}
				
				try {
				    umaContaDestino = contaAppService.recuperaUmaConta(id_conta_destino);
				}
				catch (ContaNaoEncontradaException e) {
				    System.out.println('\n' + e.getMessage());
				    break;
				}
				
				valor = Console.readDouble('\n' + "Digite o valor a ser transferido: ");
				Transferencia transferencia = new Transferencia(valor, umaContaOrigem, umaContaDestino);

				long n = transferenciaAppService.inclui(transferencia);

				System.out.println('\n' + "Transfer�ncia "+n+" realizada com sucesso!");

				break;
			}

			case 2: {
				int resposta = Console.readInt('\n' + "Digite o �ndice da transfer�ncia que voc� deseja remover: ");

				try {
					umaTransferencia = transferenciaAppService.recuperaUmaTransferencia(resposta);
				} catch (TransferenciaNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "�ndice = " + umaTransferencia.getId() + '\n'
				+ "N�mero da conta de origem = " + umaTransferencia.getContaOrigem().getNumero() + '\n'
				+ "N�mero da conta de destino = " + umaTransferencia.getContaDestino().getNumero() + '\n' 
				+ "Valor da transferencia: = " + umaTransferencia.getValor());

				String resp = Console.readLine('\n' + "Confirma a remo��o da transf�rencia?");

				if (resp.equals("s")) {
					try {
						transferenciaAppService.exclui(umaTransferencia);
						System.out.println('\n' + "Transfer�ncia removida com sucesso!");
					} catch (TransferenciaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
				} else {
					System.out.println('\n' + "Transfer�ncia n�o removida.");
				}

				break;
			}
			
			case 3: {
				List<Transferencia> transferencias = transferenciaAppService.recuperaListaDeTransferencias();
				for (Transferencia transferencia : transferencias) {
					System.out.println('\n' + "�ndice = " + transferencia.getId()
					+ "N�mero da conta de origem = " + transferencia.getContaOrigem().getNumero() + '\n'
					+ "N�mero da conta de destino = " + transferencia.getContaDestino().getNumero() + '\n' 
					+ "Valor da transferencia: = " + transferencia.getValor());
				}
				
				break;
			}
			
			case 4: {
				long id_conta = Console.readInt('\n' + "Informe o �ndice da conta: ");
				
				try {
				    umaContaOrigem = contaAppService.recuperaUmaConta(id_conta);
				}
				catch (ContaNaoEncontradaException e) {
				    System.out.println('\n' + e.getMessage());
				    break;
				}
				List<Transferencia> transferencias = transferenciaAppService.recuperaListaDeTransferencias();
				
				if(transferencias.size() == 0) {
					System.out.println('\n'+"Nenhuma transfer�ncia encontrada."+'\n');
					break;
				} else {
					System.out.println('\n'+"Valores das transfer�ncias recebidas e efetuadas pela conta de n�mero "+umaContaOrigem.getNumero()+":"+'\n');
					for (Transferencia t : transferencias) {
						if(t.getContaOrigem().getId() == id_conta) {
							System.out.println("Transfer�ncia "+ t.getId() + " -> Valor transferido: R$ " + t.getValor());
						}
						else if(t.getContaDestino().getId() == id_conta) {
							System.out.println("Transfer�ncia "+ t.getId() + " -> Valor recebido: R$ " + t.getValor());
						}
					}
					
					break;
				}
			}
			case 5: {
				continua = false;
				break;
			}

			default:
				System.out.println('\n' + "Op��o inv�lida!");
			}
}}}
