package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.AgenciaDAO;
import excecao.ObjetoNaoEncontradoException;
import excecao.AgenciaNaoEncontradaException;
import excecao.NumeroDeAgenciaInvalidoException;
import modelo.Agencia;

@Service
public class AgenciaAppService {
	
	@Autowired
	private AgenciaDAO agenciaDAO;
	
	@Transactional
	public long inclui(Agencia agencia) throws NumeroDeAgenciaInvalidoException {
		String agenciaS = agencia.toString();
		if(agenciaS.length() > 4 || agenciaS.length() < 4) {
			throw new NumeroDeAgenciaInvalidoException("Número de agência inválido!");
		}
		agenciaDAO.inclui(agencia);
		return agencia.getId();
	}

	@Transactional
	public void altera(Agencia agencia) throws AgenciaNaoEncontradaException {
		try {
			agenciaDAO.getPorIdComLock(agencia.getId());
			agenciaDAO.altera(agencia);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AgenciaNaoEncontradaException("Agência não encontrada");
		}
	}

	@Transactional
	public void exclui(Agencia ag) throws AgenciaNaoEncontradaException {
		try {
			Agencia agencia = agenciaDAO.recuperaAgencia(ag.getId());

			agenciaDAO.exclui(agencia);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AgenciaNaoEncontradaException("Agência não encontrada");
		}
	}

	public Agencia recuperaUmaAgencia(long numero) throws AgenciaNaoEncontradaException {
		try {
			return agenciaDAO.getPorId(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AgenciaNaoEncontradaException("Agência não encontrada");
		}
	}

	public Agencia recuperaAgenciaEContas(long numero) throws AgenciaNaoEncontradaException {
		try {
			return agenciaDAO.recuperaAgencia(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new AgenciaNaoEncontradaException("Agência não encontrada");
		}
	}

	public List<Agencia> recuperaListaDeAgencias() {
		return agenciaDAO.recuperaListaDeAgencias();
	}
}