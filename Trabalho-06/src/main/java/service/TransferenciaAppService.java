package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.ContaDAO;
import dao.TransferenciaDAO;
import excecao.ObjetoNaoEncontradoException;
import excecao.TransferenciaNaoEncontradaException;
import excecao.ContaNaoEncontradaException;
import modelo.Conta;
import modelo.Transferencia;

@Service
public class TransferenciaAppService {
	
	@Autowired
	private ContaDAO contaDAO;
	
	@Autowired
	private TransferenciaDAO transferenciaDAO;
	
	@Transactional
	public long inclui(Transferencia transferencia) throws ContaNaoEncontradaException {
		Conta contaOrigem = transferencia.getContaOrigem();
		Conta contaDestino = transferencia.getContaDestino();
		
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
		
		Transferencia t = transferenciaDAO.inclui(transferencia);
		return t.getId();
	}

	@Transactional
	public void altera(Transferencia transferencia) throws TransferenciaNaoEncontradaException {
		try {
			transferenciaDAO.getPorIdComLock(transferencia.getId());
			transferenciaDAO.altera(transferencia);
		} catch (ObjetoNaoEncontradoException e) {
			throw new TransferenciaNaoEncontradaException("Transfêrencia não encontrada");
		}
	}

	@Transactional
	public void exclui(Transferencia tr) throws TransferenciaNaoEncontradaException {
		try {
			Transferencia transferencia = transferenciaDAO.recuperaTransferencia(tr.getId());

			transferenciaDAO.exclui(transferencia);
		} catch (ObjetoNaoEncontradoException e) {
			throw new TransferenciaNaoEncontradaException("Transfêrencia não encontrada");
		}
	}

	public Transferencia recuperaUmaTransferencia(long id) throws TransferenciaNaoEncontradaException {
		try {
			return transferenciaDAO.getPorId(id);
		} catch (ObjetoNaoEncontradoException e) {
			throw new TransferenciaNaoEncontradaException("Transfêrencia não encontrada");
		}
	}

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