package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import anotacao.Perfil;
import dao.AgenciaDAO;
import excecao.ObjetoNaoEncontradoException;
import excecao.AgenciaNaoEncontradaException;
import excecao.NumeroDeAgenciaInvalidoException;
import modelo.Agencia;

public class AgenciaAppService {
	
	private AgenciaDAO agenciaDAO;
	
	@Autowired
	public void setAgenciaDAO(AgenciaDAO agenciaDAO) {
		this.agenciaDAO = agenciaDAO;
	}
	
	@Perfil(nomes={"admin"})
	@Transactional
	public long inclui(Agencia agencia) throws NumeroDeAgenciaInvalidoException {
		if(agencia.getNumero() > 9999 || agencia.getNumero() < 1000) {
			throw new NumeroDeAgenciaInvalidoException("Número de agência inválido!");
		}
		agenciaDAO.inclui(agencia);
		return agencia.getId();
	}

	@Perfil(nomes={"admin"})
	@Transactional
	public void altera(Agencia agencia) throws AgenciaNaoEncontradaException {
		try {
			agenciaDAO.getPorIdComLock(agencia.getId());
			agenciaDAO.altera(agencia);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AgenciaNaoEncontradaException("Agência não encontrada");
		}
	}

	@Perfil(nomes={"admin"})
	@Transactional
	public void exclui(Agencia ag) throws AgenciaNaoEncontradaException {
		try {
			Agencia agencia = agenciaDAO.recuperaAgencia(ag.getId());

//			if(produto.getLances().size() > 0)
//			{	throw new ProdutoNaoEncontradoException("Este produto possui lances e não pode ser removido");
//			}

			agenciaDAO.exclui(agencia);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AgenciaNaoEncontradaException("Agência não encontrada");
		}
	}

	@Perfil(nomes={"admin"})
	public Agencia recuperaUmaAgencia(long numero) throws AgenciaNaoEncontradaException {
		try {
			return agenciaDAO.getPorId(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AgenciaNaoEncontradaException("Agência não encontrada");
		}
	}

	@Perfil(nomes={"admin"})
	public Agencia recuperaAgenciaEContas(long numero) throws AgenciaNaoEncontradaException {
		try {
			return agenciaDAO.recuperaAgencia(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AgenciaNaoEncontradaException("Agência não encontrada");
		}
	}

	@Perfil(nomes={"admin", "user"})
	public List<Agencia> recuperaListaDeAgencias() {
		return agenciaDAO.recuperaListaDeAgencias();
	}
}