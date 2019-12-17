package aspecto;

import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.reflections.Reflections;
import org.springframework.dao.DataIntegrityViolationException;

import anotacao.ConstraintViolada;
import excecao.ViolacaoDeConstraintDesconhecidaException;

@Aspect
public class AspectoAround {
	private static Map<String, Class<?>> map = new HashMap<String, Class<?>>();
	private static List<String> listaDeNomesDeConstraints;

	static {
		Reflections reflections = new Reflections("excecao");
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ConstraintViolada.class);
		for (Class<?> classe : classes) {
			String nomeDaConstraint = classe.getAnnotation(ConstraintViolada.class).nome();
			map.put(nomeDaConstraint, classe);
		}
		listaDeNomesDeConstraints = new ArrayList<String>(map.keySet());
	}

	@Pointcut("execution(* service.AgenciaAppService.*(..))")
	public void traduzExcecaoAround() {
	}

	@Around("traduzExcecaoAround()")
	public Object traduzExcecaoAround(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return joinPoint.proceed();
		} catch (Throwable e) {
			Throwable t = e;

			if (t instanceof DataIntegrityViolationException) {
				t = t.getCause();
				while (t != null && !(t instanceof SQLException)) {
					t = t.getCause();
				}

				String msg = (t.getMessage() != null) ? t.getMessage() : "";
				msg = msg.toUpperCase();

				for (String nomeConstraint : listaDeNomesDeConstraints) {
					if (msg.indexOf(nomeConstraint) != -1) {
						Class<?> classe = map.get(nomeConstraint);
						String mensagem = classe.getAnnotation(ConstraintViolada.class).msg();
						Constructor<?> constructor = classe.getConstructor(String.class);
						throw (Exception) constructor.newInstance(mensagem);
					}
				}
				throw new ViolacaoDeConstraintDesconhecidaException("A operação não pode ser realizada.");
			} else {
				throw e;
			}
		}
	}
}