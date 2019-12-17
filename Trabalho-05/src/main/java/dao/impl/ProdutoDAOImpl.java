package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import dao.ProdutoDAO;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import modelo.Produto;

@Repository
public class ProdutoDAOImpl implements ProdutoDAO {
	
	@PersistenceContext
	protected EntityManager em;
	
	public long inclui(Produto umProduto) {
		try {
			em.persist(umProduto);

			return umProduto.getId();
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	public void altera(Produto umProduto) throws ObjetoNaoEncontradoException {
		try {
			Produto produto = em.find(Produto.class, umProduto.getId(), LockModeType.PESSIMISTIC_WRITE);

			if (produto == null) {
				throw new ObjetoNaoEncontradoException();
			}

			em.merge(umProduto);
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	public void exclui(long id) throws ObjetoNaoEncontradoException {
		try {
			Produto produto = em.find(Produto.class, id, LockModeType.PESSIMISTIC_WRITE);

			if (produto == null) {
				throw new ObjetoNaoEncontradoException();
			}

			em.remove(produto);
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	public Produto recuperaUmProduto(long id) throws ObjetoNaoEncontradoException {
		try {
			Produto umProduto = (Produto) em.find(Produto.class, new Long(id));

			if (umProduto == null) {
				throw new ObjetoNaoEncontradoException();
			}

			return umProduto;
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	public Produto recuperaUmProdutoComLock(long id) throws ObjetoNaoEncontradoException {
		try {
			Produto umProduto = em.find(Produto.class, id, LockModeType.PESSIMISTIC_WRITE);

			if (umProduto == null) {
				throw new ObjetoNaoEncontradoException();
			}

			return umProduto;
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Produto> recuperaProdutos() {
		try {
			List<Produto> produtos = em.createQuery("select p from Produto p order by p.id asc").getResultList();

			return produtos;
		} catch (RuntimeException e) {
			throw new InfraestruturaException(e);
		}
	}
}