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
			System.out.println('\n' + "O que você deseja fazer?");
			System.out.println('\n' + "1. Inserir uma nova transferência entre contas");
			System.out.println("2. Remover uma transferência do registro");
			System.out.println("3. Listar todas as transferências");
			System.out.println("4. Listar todas as transferências feitas e recebidas por uma conta");
			System.out.println("5. Sair");

			int opcao = Console.readInt('\n' + "Digite um número entre 1 e 5:");

			switch (opcao) {
			case 1: {
				long id_conta_origem = Console.readInt('\n' + "Informe o índice da conta de origem: ");
				long id_conta_destino = Console.readInt('\n' + "Informe o índice da conta de destino: ");
				
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
					throw new SaldoInsuficienteException("Valor inválido!");
				} else {
					umaContaOrigem.setSaldo(umaContaOrigem.getSaldo()-valor);
					umaContaDestino.setSaldo(umaContaDestino.getSaldo()+valor);
					contaAppService.altera(umaContaOrigem);
					contaAppService.altera(umaContaDestino);
				}
				
				Transferencia transferencia = new Transferencia(valor, umaContaOrigem, umaContaDestino);

				long n = transferenciaAppService.inclui(transferencia);

				System.out.println('\n' + "Transferência "+n+" realizada com sucesso!");

				break;
			}

			case 2: {
				int resposta = Console.readInt('\n' + "Digite o índice da transferência que você deseja remover: ");

				try {
					umaTransferencia = transferenciaAppService.recuperaUmaTransferencia(resposta);
				} catch (TransferenciaNaoEncontradaException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Índice = " + umaTransferencia.getId() + '\n'
				+ "Número da conta de origem = " + umaTransferencia.getContaOrigem().getNumero() + '\n'
				+ "Número da conta de destino = " + umaTransferencia.getContaDestino().getNumero() + '\n' 
				+ "Valor da transferencia: = " + umaTransferencia.getValor());

				String resp = Console.readLine('\n' + "Confirma a remoção da transfêrencia?");

				if (resp.equals("s")) {
					try {
						transferenciaAppService.exclui(umaTransferencia);
						System.out.println('\n' + "Transferência removida com sucesso!");
					} catch (TransferenciaNaoEncontradaException e) {
						System.out.println('\n' + e.getMessage());
					}
				} else {
					System.out.println('\n' + "Transferência não removida.");
				}

				break;
			}
			
			case 3: {
				List<Transferencia> transferencias = transferenciaAppService.recuperaListaDeTransferencias();
				for (Transferencia transferencia : transferencias) {
					System.out.println('\n' + "Índice = " + transferencia.getId()
					+ "Número da conta de origem = " + transferencia.getContaOrigem().getNumero() + '\n'
					+ "Número da conta de destino = " + transferencia.getContaDestino().getNumero() + '\n' 
					+ "Valor da transferencia: = " + transferencia.getValor());
				}
				
				break;
			}
			
			case 4: {
				long id_conta = Console.readInt('\n' + "Informe o índice da conta: ");
				
				try {
				    umaContaOrigem = contaAppService.recuperaUmaConta(id_conta);
				}
				catch (ContaNaoEncontradaException e) {
				    System.out.println('\n' + e.getMessage());
				    break;
				}
				List<Transferencia> transferencias = transferenciaAppService.recuperaListaDeTransferencias();
				
				if(transferencias.size() == 0) {
					System.out.println('\n'+"Nenhuma transferência encontrada."+'\n');
					break;
				} else {
					System.out.println('\n'+"Valores das transferências recebidas e efetuadas pela conta de número "+umaContaOrigem.getNumero()+":"+'\n');
					for (Transferencia t : transferencias) {
						if(t.getContaOrigem().getId() == id_conta) {
							System.out.println("Transferência "+ t.getId() + " -> Valor transferido: R$ " + t.getValor());
						}
						else if(t.getContaDestino().getId() == id_conta) {
							System.out.println("Transferência "+ t.getId() + " -> Valor recebido: R$ " + t.getValor());
						}
					}
					
					break;
				}
			}
/*
			case 5: {
				long n = Console.readInt('\n' + "Informe o número da agência: ");

				try {
					umaAgencia = agenciaAppService.recuperaAgenciaEContas(n);
				} catch (ProdutoNaoEncontradoException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "Número = " + umaAgencia.getId() + "    Nome = " + umaAgencia.getNome()
				+ "    Endereço = " + umaAgencia.getEndereco() + "      Número da agência = "
										+ umaAgencia.getNumero() );

				List<Conta> contas = umaAgencia.getContas();

				for (Conta conta : contas) {
					System.out.println('\n' + "      Conta " + conta.getId() + " -  Número = " + conta.getNumero()
							+ "  Titular = " + conta.getTitular());
				}

				break;
			}

			case 6: {
				List<Agencia> agencias = agenciaAppService.recuperaListaDeAgenciasEContas();

				if (agencias.size() != 0) {
					System.out.println("");

					for (Agencia agencia : agencias) {
						System.out.println('\n' + "Agência " + agencia.getId() + " -  Nome = "
								+ agencia.getNome() + "  Número da agência = " + agencia.getNumero() +
								"  Endereço = " + agencia.getEndereco());

						List<Conta> contas = agencia.getContas();

						for (Conta conta : contas) {
							System.out.println('\n' + "Conta " + conta.getId() + " -  Número = " + conta.getNumero()
							+ "  Titular = " + conta.getTitular());
						}
					}
				} else {
					System.out.println('\n' + "Não há agências cadastradas.");
				}

				break;
			}
*/
			case 5: {
				continua = false;
				break;
			}

			default:
				System.out.println('\n' + "Opção inválida!");
			}
}}}
