package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import anotacao.Perfil;
import dao.ContaDAO;
import dao.TransferenciaDAO;
import excecao.ObjetoNaoEncontradoException;
import excecao.SaldoInsuficienteException;
import excecao.TransferenciaNaoEncontradaException;
import excecao.ContaNaoEncontradaException;
import modelo.Conta;
import modelo.Transferencia;

public class TransferenciaAppService {
	
	private ContaDAO contaDAO;
	private TransferenciaDAO transferenciaDAO;
	
	public void setContaDAO(ContaDAO contaDAO) {
		this.contaDAO = contaDAO;
	}
	
	public void setTransferenciaDAO(TransferenciaDAO transferenciaDAO) {
		this.transferenciaDAO = transferenciaDAO;
	}
	
	@Perfil(nomes={"admin", "user"})
	@Transactional
	public long inclui(Transferencia transferencia) throws ContaNaoEncontradaException, SaldoInsuficienteException {
		Conta contaOrigem = transferencia.getContaOrigem();
		Conta contaDestino = transferencia.getContaDestino();
		double valor = transferencia.getValor();
		try {
			contaOrigem = contaDAO.getPorIdComLock(contaOrigem.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new ContaNaoEncontradaException("Conta de origem inexistente");
		}
		
		try {
			contaDestino = contaDAO.getPorIdComLock(contaDestino.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new ContaNaoEncontradaException("Conta destino inexistente");
		}
		
		if(valor > contaOrigem.getSaldo()) {
			throw new SaldoInsuficienteException("Valor inválido!");
		} else {
			contaOrigem.setSaldo(contaOrigem.getSaldo()-valor);
			contaDestino.setSaldo(contaDestino.getSaldo()+valor);
		}
		
		Transferencia t = transferenciaDAO.inclui(transferencia);
		return t.getId();
	}

	@Perfil(nomes={"admin", "user"})
	@Transactional
	public void altera(Transferencia transferencia) throws TransferenciaNaoEncontradaException {
		try {
			transferenciaDAO.getPorIdComLock(transferencia.getId());
			transferenciaDAO.altera(transferencia);
		} catch (ObjetoNaoEncontradoException e) {
			throw new TransferenciaNaoEncontradaException("Transfêrencia não encontrada");
		}
	}

	@Perfil(nomes={"admin", "user"})
	@Transactional
	public void exclui(Transferencia tr) throws TransferenciaNaoEncontradaException {
		try {
			Transferencia transferencia = transferenciaDAO.recuperaTransferencia(tr.getId());

			transferenciaDAO.exclui(transferencia);
		} catch (ObjetoNaoEncontradoException e) {
			throw new TransferenciaNaoEncontradaException("Transfêrencia não encontrada");
		}
	}

	@Perfil(nomes={"admin", "user"})
	public Transferencia recuperaUmaTransferencia(long id) throws TransferenciaNaoEncontradaException {
		try {
			return transferenciaDAO.getPorId(id);
		} catch (ObjetoNaoEncontradoException e) {
			throw new TransferenciaNaoEncontradaException("Transfêrencia não encontrada");
		}
	}

	@Perfil(nomes={"admin", "user"})
	public Transferencia recuperaTransferenciaEContas(long id) throws TransferenciaNaoEncontradaException {
		try {
			return transferenciaDAO.recuperaTransferencia(id);
		} catch (ObjetoNaoEncontradoException e) {
			throw new TransferenciaNaoEncontradaException("Transfêrencia não encontrada");
		}
	}

	public List<Transferencia> recuperaListaDeTransferencias() {
		return transferenciaDAO.recuperaListaDeTransferencias();
	}
}