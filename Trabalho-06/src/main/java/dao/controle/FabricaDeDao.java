package dao.controle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dao.impl.AgenciaDAOImpl;
import dao.impl.ClienteDAOImpl;
import dao.impl.ContaDAOImpl;
import dao.impl.TransferenciaDAOImpl;
import net.sf.cglib.proxy.Enhancer;

@Configuration
public class FabricaDeDao {

	@Bean
	public static AgenciaDAOImpl getAgenciaDao() throws Exception {
		return getDao(dao.impl.AgenciaDAOImpl.class);
	}
	
	@Bean
	public static ClienteDAOImpl getClienteDao() throws Exception {
		return getDao(dao.impl.ClienteDAOImpl.class);
	}
	
	@Bean
	public static ContaDAOImpl getContaDao() throws Exception {
		return getDao(dao.impl.ContaDAOImpl.class);
	}
	
	@Bean
	public static TransferenciaDAOImpl getTransferenciaDao() throws Exception {
		return getDao(dao.impl.TransferenciaDAOImpl.class);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getDao(Class<T> classeDoDao) throws Exception {
		return (T) Enhancer.create(classeDoDao, new InterceptadorDeDAO());
	}
}