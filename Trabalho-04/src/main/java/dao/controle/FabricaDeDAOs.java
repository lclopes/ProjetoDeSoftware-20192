package dao.controle;

import java.lang.reflect.Field;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import org.reflections.Reflections;

import anotacao.PersistenceContext;
import net.sf.cglib.proxy.Enhancer;
import servico.controle.JPAUtil;

public class FabricaDeDAOs {

	@SuppressWarnings("unchecked")
	public static <T> T getDAO(Class<T> type) {
				
		Reflections reflections = new Reflections("dao.impl");
 
		Set<Class<? extends T>> implClass = reflections.getSubTypesOf(type);

		if (implClass.size() > 1) {
			throw new RuntimeException
		       ("Não pode existir mais de uma classe implementando "
		      + "a inteface " + type.getName());
		}

		Class<?> classe = implClass.iterator().next();

		T proxy = (T)Enhancer.create(classe, new InterceptadorDeDAO());

		
		return proxy;
	}
}
