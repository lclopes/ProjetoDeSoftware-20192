package dao.impl;

import org.springframework.stereotype.Repository;

import dao.ContaDAO;
import modelo.Conta;

@Repository
public abstract class ContaDAOImpl extends JPADaoGenerico<Conta, Long> implements ContaDAO {
    public ContaDAOImpl() {
	    super(Conta.class);
    }
}
