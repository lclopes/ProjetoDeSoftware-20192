package dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import dao.LoginDAO;
import modelo.Usuario;

@Repository
public class LoginDAOImpl implements LoginDAO {

	@PersistenceContext
	protected EntityManager em;

	public Usuario loga(String conta, String senha){
		String busca = "select u from Usuario u left outer join fetch u.perfis where u.conta = :conta and u.senha = :senha";
		
		try {
			Usuario umUsuario = (Usuario) em.createQuery(busca).setParameter("conta", conta).setParameter("senha", senha).getSingleResult();
		
			System.out.println("Bem-vindo, " + umUsuario.getConta() + ". Você possui " + umUsuario.getPerfis().size() + " perfil(is).");
			
			return umUsuario;

		}
		catch (NoResultException e) {
			return null;
		}
	}
}
