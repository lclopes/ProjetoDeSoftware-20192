package dao.impl;

import org.springframework.stereotype.Repository;

import dao.AgenciaDAO;
import modelo.Agencia;

@Repository
public abstract class AgenciaDAOImpl extends JPADaoGenerico<Agencia, Long> implements AgenciaDAO {
    public AgenciaDAOImpl() {
	    super(Agencia.class);
    }
}
