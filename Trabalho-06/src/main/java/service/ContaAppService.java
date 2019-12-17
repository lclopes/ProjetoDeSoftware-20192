package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.AgenciaDAO;
import dao.ClienteDAO;
import dao.ContaDAO;
import excecao.ObjetoNaoEncontradoException;
import excecao.AgenciaNaoEncontradaException;
import excecao.ClienteNaoEncontradoException;
import excecao.ContaNaoEncontradaException;
import modelo.Agencia;
import modelo.Cliente;
import modelo.Conta;

@Service
public class ContaAppService {
	
	@Autowired
	private ContaDAO contaDAO;
	
	@Autowired
	private AgenciaDAO agenciaDAO;
	
	@Autowired
	private ClienteDAO clienteDAO;

	@Transactional
	public long inclui(Conta conta) throws AgenciaNaoEncontradaException, ClienteNaoEncontradoException {
		Agencia agencia = conta.getAgencia();
		Cliente cliente = conta.getCliente();
		
		try {
			agencia = agenciaDAO.getPorIdComLock(agencia.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new AgenciaNaoEncontradaException("Agência inexistente");
		}
		
		try {
			cliente = clienteDAO.getPorIdComLock(cliente.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new ClienteNaoEncontradoException("Cliente inexistente");
		}
		
		Conta c = contaDAO.inclui(conta);
		return c.getId();
	}

	@Transactional
	public void altera(Conta conta) throws ContaNaoEncontradaException {
		try {
			contaDAO.getPorIdComLock(conta.getId());
			contaDAO.altera(conta);
		} catch (ObjetoNaoEncontradoException e) {
			throw new ContaNaoEncontradaException("Conta não encontrada");
		}
	}

	@Transactional
	public void exclui(Conta ct) throws AgenciaNaoEncontradaException {
		try {
			Conta conta = contaDAO.recuperaConta(ct.getId());
			contaDAO.exclui(conta);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AgenciaNaoEncontradaException("Conta não encontrada");
		}
	}

	public Conta recuperaUmaConta(long numero) throws ContaNaoEncontradaException {
		try {
			return contaDAO.getPorId(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new ContaNaoEncontradaException("Conta não encontrada");
		}
	}

	public boolean verificaCliente(long numero) {
		try {
			contaDAO.getPorId(numero);
		} catch (ObjetoNaoEncontradoException e) {
			return true;
		}
		return false;
	}
	
	public Conta recuperaContasEAgencia(long numero) throws ContaNaoEncontradaException {
		try {
			return contaDAO.recuperaConta(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new ContaNaoEncontradaException("Conta não encontrada");
		}
	}

	public List<Conta> recuperaListaDeContas() {
		return contaDAO.recuperaListaDeContas();
	}
}