//package aspecto;
//
//import java.lang.reflect.Method;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
//import anotacao.Perfil;
//import singleton.SingletonPerfis;
//
//@Aspect
//public class AspectoParaPerfil {
//    
//	/*
//	@Before(value = "@within(anotacao.Perfil) || @annotation(anotacao.Perfil)")
//    public void before(JoinPoint joinPoint) throws Throwable {
//        System.out.println("perfil.before, class: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + ", method: " + joinPoint.getSignature().getName());
//    }
//
//    @After(value = "@within(anotacao.Perfil) || @annotation(anotacao.Perfil)")
////    public void after(JoinPoint joinPoint) throws Throwable {
//    	System.out.println("perfil.after, class: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + ", method: " + joinPoint.getSignature().getName());
//    }
//    
//    */
//	
//	//@Pointcut("@annotation(anotacao.Perfil)")
//    @Pointcut("execution(* service.AgenciaAppService.*(..)) ||  "
//    		+ "execution(* service.ContaAppService.*(..)) ||  "
//    		+ "execution(* service.ClienteAppService.*(..)) ||  "
//    		+ "execution(* service.TransferenciaAppService.*(..))")
//	public void verificaPerfis() {
//    }
//
//	@Around("verificaPerfis()")
//    public Object verificaPerfilUsuario(ProceedingJoinPoint joinPoint) throws Throwable{
//        
//    	String[] perfisDoUsuarioLogado = SingletonPerfis.getSingletonPerfis().getPerfis();
//
//    	System.out.println('\n'+"O usuário possui os seguintes perfis:");
//    	for(String perfil : perfisDoUsuarioLogado) {	
//    		System.out.println(perfil);
//    	}
//    	
//    	final String methodName = joinPoint.getSignature().getName();
//    	
//    	final MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
//    	
//    	Method method = methodSignature.getMethod();
//    	
//    	if (method.getDeclaringClass().isInterface()) {
//    	    try {
//				method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
//			} catch (NoSuchMethodException | SecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}    
//    	}
//    	
//    	Perfil anotacaoPerfil = method.getAnnotation(Perfil.class);
//    	
//    	//Significa que o método buscado não possui a anotaçao de Perfil, 
//    	//logo pode ser executado normalmente
//    	if(anotacaoPerfil == null) {
//    		return joinPoint.proceed();	
//    	}
//    	else {
//    		String[] perfisPermitidos = method.getAnnotation(Perfil.class).nomes();
//
//        	System.out.println('\n' + "O método permite os seguintes perfis:");
//	    	for(String perfil : perfisPermitidos) {
//	    		System.out.println(perfil);
//	    	}
//	    	
//	    	for(String perfilMetodo : perfisPermitidos) {
//	    		for(String perfilUser : perfisDoUsuarioLogado) {
//	    			if(perfilMetodo.equals(perfilUser)) {
//	    				return joinPoint.proceed();
//	    				//Significa que o usuário tem permissão para acessar o método
//	    			}
//	    		}
//	    	}
//	    	throw new Throwable("Usuário logado no momento não possui permissão de acesso ao método");
//    	}
//    }
//}
