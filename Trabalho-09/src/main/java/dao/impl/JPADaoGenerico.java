package dao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dao.DaoGenerico;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;

/**
 * A implementação de um DAO genérico para a JPA Uma implementação "typesafe"
 * dos métodos CRUD e dos métodos de busca.
 */
public class JPADaoGenerico<T, PK extends Serializable> implements DaoGenerico<T, PK> {
    private Class<T> tipo;

    @PersistenceContext
    protected EntityManager em; // Para subclasses enchergarem

    public JPADaoGenerico(Class<T> tipo) {
	this.tipo = tipo;
    }

    public final T inclui(T o) {
	try {
	    em.persist(o);
	    em.flush();
	}
	catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}

	return o;
    }

    public final void altera(T o) {
	try {
	    em.merge(o);
	}
	catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public final void exclui(T o) {
	try {
	    em.remove(o);
	    em.flush();
	}
	catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    public final T getPorId(PK id) throws ObjetoNaoEncontradoException {
	T t = null;
	try {
	    t = em.find(tipo, id);

	    if (t == null) {
		throw new ObjetoNaoEncontradoException();
	    }
	}
	catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
	return t;
    }

    public final T getPorIdComLock(PK id) throws ObjetoNaoEncontradoException {
	T t = null;
	try {
	    t = em.find(tipo, id, LockModeType.PESSIMISTIC_WRITE);

	    if (t == null) {
		throw new ObjetoNaoEncontradoException();
	    }
	}
	catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}

	return t;
    }

    @SuppressWarnings("unchecked")
    public final T busca(Method metodo, Object[] argumentos) throws ObjetoNaoEncontradoException {
	T t = null;
	try {
	    String nomeDaBusca = getNomeDaBuscaPeloMetodo(metodo);
	    Query namedQuery = em.createNamedQuery(nomeDaBusca);

	    if (argumentos != null) {
		for (int i = 0; i < argumentos.length; i++) {
		    Object arg = argumentos[i];
		    namedQuery.setParameter(i + 1, arg); // Parâmetros de buscas são 1-based.
		}
	    }
	    t = (T) namedQuery.getSingleResult();

	    return t;
	}
	catch (NoResultException e) {
	    throw new ObjetoNaoEncontradoException();
	}
	catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    @SuppressWarnings("unchecked")
    public final T buscaUltimoOuPrimeiro(Method metodo, Object[] argumentos) throws ObjetoNaoEncontradoException {
	System.out.println("Dentro de buscaUltimoOuPrimeiro = " + this.getClass().getName());
	// Dentro de buscaUltimoOuPrimeiro =
	// dao.impl.ProdutoDAOImpl$$EnhancerByCGLIB$$fd6d4de1

	T t = null;
	try {
	    List<T> lista;
	    String nomeDaBusca = getNomeDaBuscaPeloMetodo(metodo);
	    Query namedQuery = em.createNamedQuery(nomeDaBusca);

	    if (argumentos != null) {
		for (int i = 0; i < argumentos.length; i++) {
		    Object arg = argumentos[i];
		    namedQuery.setParameter(i + 1, arg);
		}
	    }
	    lista = namedQuery.getResultList();

	    t = (lista.size() == 0) ? null : lista.get(0);

	    if (t == null) {
		throw new ObjetoNaoEncontradoException();
	    }

	    return t;
	}
	catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    @SuppressWarnings("unchecked")
    public final List<T> buscaLista(Method metodo, Object[] argumentos) {
	try {
	    System.out.println("Dentro de buscaLista = " + this.getClass().getName());
	    String nomeDaBusca = getNomeDaBuscaPeloMetodo(metodo);
	    Query namedQuery = em.createNamedQuery(nomeDaBusca);

	    if (argumentos != null) {
		for (int i = 0; i < argumentos.length; i++) {
		    Object arg = argumentos[i];
		    namedQuery.setParameter(i + 1, arg); // Parâmetros de buscas são 1-based.
		}
	    }
	    return (List<T>) namedQuery.getResultList();
	}
	catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    @SuppressWarnings("unchecked")
    public final Set<T> buscaConjunto(Method metodo, Object[] argumentos) {
	try {
	    String nomeDaBusca = getNomeDaBuscaPeloMetodo(metodo);
	    Query namedQuery = em.createNamedQuery(nomeDaBusca);

	    if (argumentos != null) {
		for (int i = 0; i < argumentos.length; i++) {
		    Object arg = argumentos[i];
		    namedQuery.setParameter(i + 1, arg); // Parâmetros de buscas são 1-based.
		}
	    }

	    List<T> lista = namedQuery.getResultList();

	    return new LinkedHashSet<T>(lista);
	}
	catch (RuntimeException e) {
	    throw new InfraestruturaException(e);
	}
    }

    // Se uma subclasse (Proxy) declara um método (getNomeDaBuscaPeloMetodo())
    // com a mesma assinatura de um método PRIVADO existente na classe pai
    // (JPADaoGenerico), o método da subclasse não efetua o override
    // do método da superclasse. Logo, ele nunca será executado utilizando
    // o polimorfismo.

    // Se mudarmos para public o método getNomeDaBuscaPeloMetodo() será
    // executado, pois esse método não foi definido como final.

    // O proxy só faz o override dos métodos públicos não final.

    // Examinar também como o Spring cria o proxy do DAO utilizando a cglib.

    private String getNomeDaBuscaPeloMetodo(Method metodo) {
	System.out.println("Dentro de getNomeDaBuscaPeloMetodo() = " + this.getClass().getName());
	return tipo.getSimpleName() + "." + metodo.getName();
    }
}