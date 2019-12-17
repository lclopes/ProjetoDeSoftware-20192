package servico;

import java.util.List;

import dao.ContaDAO;
import dao.controle.FabricaDeDAOs;
import excecao.ContaNaoEncontradaException;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import modelo.Conta;
import util.JPAUtil;

public class ContaAppService {
    private static ContaDAO contaDAO = FabricaDeDAOs.getDAO(ContaDAO.class);

    public long inclui(Conta umConta) {
	try {

	    JPAUtil.beginTransaction();

	    long numero = contaDAO.inclui(umConta);

	    JPAUtil.commitTransaction();

	    return numero;
	} catch (InfraestruturaException e) {
	    try {
		JPAUtil.rollbackTransaction();
	    } catch (InfraestruturaException ie) {
	    }

	    throw e;
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public void transfereValor(Conta contaDebitada, Conta contaCreditada, double valor)
	    throws ContaNaoEncontradaException {
	try {
	    JPAUtil.beginTransaction();

	    try {
		this.debita(contaDebitada, valor);
	    } catch (ContaNaoEncontradaException e) {
		JPAUtil.rollbackTransaction();
		throw e;
	    }
	    
	    try {
		this.credita(contaCreditada, valor);
	    } catch (ContaNaoEncontradaException e) {
		JPAUtil.rollbackTransaction();
		throw e;
	    }

	    JPAUtil.commitTransaction();
	} catch (InfraestruturaException e) {
	    try {
		JPAUtil.rollbackTransaction();
	    } catch (InfraestruturaException ie) {
	    }

	    throw e;
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public void altera(Conta conta) throws ContaNaoEncontradaException {
	try {
	    JPAUtil.beginTransaction();

	    Conta umaConta = contaDAO.recuperaUmaConta(conta.getId());
	    if (umaConta == null) {
		JPAUtil.rollbackTransaction();
		throw new ContaNaoEncontradaException("Conta não encontrada");
	    }

	    contaDAO.altera(conta);

	    JPAUtil.commitTransaction();
	} catch (ObjetoNaoEncontradoException e) {
	    JPAUtil.rollbackTransaction();

	    throw new ContaNaoEncontradaException("Conta não encontrada");
	} catch (InfraestruturaException e) {
	    try {
		JPAUtil.rollbackTransaction();
	    } catch (InfraestruturaException ie) {
	    }

	    throw e;
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public void debita(Conta conta, double valor) throws ContaNaoEncontradaException {
	try {
	    JPAUtil.beginTransaction();

	    Conta umaConta = contaDAO.recuperaUmaConta(conta.getId());
	    if (umaConta == null) {
		JPAUtil.rollbackTransaction();
		throw new ContaNaoEncontradaException("Conta debitada não encontrada");
	    }
	    contaDAO.debita(umaConta, valor);

	    JPAUtil.commitTransaction();
	} catch (ObjetoNaoEncontradoException e) {
	    JPAUtil.rollbackTransaction();

	    throw new ContaNaoEncontradaException("Conta debitada não encontrada");
	} catch (InfraestruturaException e) {
	    try {
		JPAUtil.rollbackTransaction();
	    } catch (InfraestruturaException ie) {
	    }

	    throw e;
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public void credita(Conta conta, double valor) throws ContaNaoEncontradaException {
	try {
	    JPAUtil.beginTransaction();

	    Conta umaConta = contaDAO.recuperaUmaConta(conta.getId());
	    if (umaConta == null) {
		JPAUtil.rollbackTransaction();
		throw new ContaNaoEncontradaException("Conta creditada não encontrada");
	    }
	    contaDAO.credita(umaConta, valor);

	    JPAUtil.commitTransaction();
	} catch (ObjetoNaoEncontradoException e) {
	    JPAUtil.rollbackTransaction();

	    throw new ContaNaoEncontradaException("Conta creditada não encontrada");
	} catch (InfraestruturaException e) {
	    try {
		JPAUtil.rollbackTransaction();
	    } catch (InfraestruturaException ie) {
	    }

	    throw e;
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public void exclui(long numero) throws ContaNaoEncontradaException {
	try {
	    JPAUtil.beginTransaction();

	    contaDAO.exclui(numero);

	    JPAUtil.commitTransaction();
	} catch (ObjetoNaoEncontradoException e) {
	    JPAUtil.rollbackTransaction();

	    throw new ContaNaoEncontradaException("Conta não encontrado");
	} catch (InfraestruturaException e) {
	    try {
		JPAUtil.rollbackTransaction();
	    } catch (InfraestruturaException ie) {
	    }

	    throw e;
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public Conta recuperaUmaConta(long numero) throws ContaNaoEncontradaException {
	try {
	    Conta umConta = contaDAO.recuperaUmaConta(numero);

	    return umConta;
	} catch (ObjetoNaoEncontradoException e) {
	    throw new ContaNaoEncontradaException("Conta não encontrada");
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public List<Conta> recuperaContas() {
	try {
	    List<Conta> contas = contaDAO.recuperaContas();

	    return contas;
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }
}