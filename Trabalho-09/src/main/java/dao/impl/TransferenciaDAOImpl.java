package dao.impl;

import org.springframework.stereotype.Repository;

import dao.TransferenciaDAO;
import modelo.Transferencia;

@Repository
public abstract class TransferenciaDAOImpl extends JPADaoGenerico<Transferencia, Long> implements TransferenciaDAO {
    public TransferenciaDAOImpl() {
	    super(Transferencia.class);
    }
}
