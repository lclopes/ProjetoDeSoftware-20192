package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import anotacao.Perfil;
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

public class ContaAppService {
	
	private ContaDAO contaDAO;
	private AgenciaDAO agenciaDAO;
	private ClienteDAO clienteDAO;
	
	public void setContaDAO(ContaDAO contaDAO) {
		this.contaDAO = contaDAO;
	}
	
	public void setAgenciaDAO(AgenciaDAO agenciaDAO) {
		this.agenciaDAO = agenciaDAO;
	}
	
	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	@Perfil(nomes={"admin"})
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

	@Perfil(nomes={"admin"})
	@Transactional
	public void altera(Conta conta) throws ContaNaoEncontradaException {
		try {
			contaDAO.getPorIdComLock(conta.getId());
			contaDAO.altera(conta);
		} catch (ObjetoNaoEncontradoException e) {
			throw new ContaNaoEncontradaException("Conta não encontrada");
		}
	}

	@Perfil(nomes={"admin"})
	@Transactional
	public void exclui(Conta ct) throws AgenciaNaoEncontradaException {
		try {
			Conta conta = contaDAO.recuperaConta(ct.getId());
			contaDAO.exclui(conta);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AgenciaNaoEncontradaException("Conta não encontrada");
		}
	}

	@Perfil(nomes={"admin"})
	public Conta recuperaUmaConta(long numero) throws ContaNaoEncontradaException {
		try {
			return contaDAO.getPorId(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new ContaNaoEncontradaException("Conta não encontrada");
		}
	}

	@Perfil(nomes={"admin"})
	public boolean verificaCliente(long numero) {
		try {
			contaDAO.getPorId(numero);
		} catch (ObjetoNaoEncontradoException e) {
			return true;
		}
		return false;
	}
	
	@Perfil(nomes={"admin"})
	public Conta recuperaContasEAgencia(long numero) throws ContaNaoEncontradaException {
		try {
			return contaDAO.recuperaConta(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new ContaNaoEncontradaException("Conta não encontrada");
		}
	}

	@Perfil(nomes={"admin", "user"})
	public List<Conta> recuperaListaDeContas() {
		return contaDAO.recuperaListaDeContas();
	}
}