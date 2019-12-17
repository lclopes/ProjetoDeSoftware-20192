package dao.impl;

import org.springframework.stereotype.Repository;

import dao.ClienteDAO;
import modelo.Cliente;

@Repository
public abstract class ClienteDAOImpl extends JPADaoGenerico<Cliente, Long> implements ClienteDAO {
    public ClienteDAOImpl() {
	    super(Cliente.class);
    }
}
