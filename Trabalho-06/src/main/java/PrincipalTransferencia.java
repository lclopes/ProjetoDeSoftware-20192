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
import service.ContaAppService;
import service.TransferenciaAppService;

public class PrincipalTransferencia {
	public static void main(String[] args) throws AgenciaNaoEncontradaException, 
	ClienteNaoEncontradoException, TransferenciaNaoEncontradaException, SaldoInsuficienteException, 
	ContaNaoEncontradaException {
		double valor;
		Conta umaContaOrigem;
		Conta umaContaDestino;
		Transferencia umaTransferencia;
		
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");
	
		ContaAppService contaAppService = (ContaAppService) fabrica.getBean("contaAppService");
		TransferenciaAppService transferenciaAppService = (TransferenciaAppService) fabrica.getBean("transferenciaAppService");

		boolean continua = true;
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

				if(valor > umaContaOrigem.getSaldo()) {
					throw new SaldoInsuficienteException("Valor inv�lido!");
				} else {
					umaContaOrigem.setSaldo(umaContaOrigem.getSaldo()-valor);
					umaContaDestino.setSaldo(umaContaDestino.getSaldo()+valor);
					contaAppService.altera(umaContaOrigem);
					contaAppService.altera(umaContaDestino);
				}
				
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
/*
			case 5: {
				long n = Console.readInt('\n' + "Informe o n�mero da ag�ncia: ");

				try {
					umaAgencia = agenciaAppService.recuperaAgenciaEContas(n);
				} catch (ProdutoNaoEncontradoException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "N�mero = " + umaAgencia.getId() + "    Nome = " + umaAgencia.getNome()
				+ "    Endere�o = " + umaAgencia.getEndereco() + "      N�mero da ag�ncia = "
										+ umaAgencia.getNumero() );

				List<Conta> contas = umaAgencia.getContas();

				for (Conta conta : contas) {
					System.out.println('\n' + "      Conta " + conta.getId() + " -  N�mero = " + conta.getNumero()
							+ "  Titular = " + conta.getTitular());
				}

				break;
			}

			case 6: {
				List<Agencia> agencias = agenciaAppService.recuperaListaDeAgenciasEContas();

				if (agencias.size() != 0) {
					System.out.println("");

					for (Agencia agencia : agencias) {
						System.out.println('\n' + "Ag�ncia " + agencia.getId() + " -  Nome = "
								+ agencia.getNome() + "  N�mero da ag�ncia = " + agencia.getNumero() +
								"  Endere�o = " + agencia.getEndereco());

						List<Conta> contas = agencia.getContas();

						for (Conta conta : contas) {
							System.out.println('\n' + "Conta " + conta.getId() + " -  N�mero = " + conta.getNumero()
							+ "  Titular = " + conta.getTitular());
						}
					}
				} else {
					System.out.println('\n' + "N�o h� ag�ncias cadastradas.");
				}

				break;
			}
*/
			case 5: {
				continua = false;
				break;
			}

			default:
				System.out.println('\n' + "Op��o inv�lida!");
			}
}}}
