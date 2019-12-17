package servico.controle;

import java.lang.reflect.Field;
import java.util.Set;

import org.reflections.Reflections;

import anotacao.Autowired;
import dao.controle.FabricaDeDAOs;
import net.sf.cglib.proxy.Enhancer;

public class FabricaDeServico {
//    private static ResourceBundle prop;
//
//    static {
//	try {
//	    prop = ResourceBundle.getBundle("servico");
//	}
//	catch (MissingResourceException e) {
//	    System.out.println("Aquivo servico.properties não encontrado.");
//	    throw new RuntimeException(e);
//	}
//    }

    // Esse método pode ser executado de 2 formas:
    // 1. produtoAppService =
    // FabricaDeServico.<ProdutoAppService>getServico(ProdutoAppService.class);
    // 2. produtoAppService = FabricaDeServico.getServico(ProdutoAppService.class);

    @SuppressWarnings("unchecked")
    public static <T> T getServico(Class<T> type) {

	Reflections reflections = new Reflections("servico.impl");
	
	Set<Class<? extends T>> implClass = reflections.getSubTypesOf(type);
	
	if (implClass.size() > 1) {
	    throw new RuntimeException
	       ("Não pode existir mais de uma classe implementando "
	      + "a inteface " + type.getName());
	}
	
	Class<?> classe = implClass.iterator().next();
	    
	T proxy = (T)Enhancer.create(classe, new InterceptadorDeServico());

	Field[] fields = classe.getDeclaredFields();
	for (Field field : fields) {
	    if (field.isAnnotationPresent(Autowired.class)) {
		field.setAccessible(true);
		try {
		    field.set(proxy, FabricaDeDAOs.getDAO(field.getType()));
		}
		catch (IllegalArgumentException | IllegalAccessException e) {
		    throw new RuntimeException(e);
		}
	    }
	}
	    
	return proxy;
    }
}