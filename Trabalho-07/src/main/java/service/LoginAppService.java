package service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import dao.LoginDAO;
import modelo.Usuario;
@Service
public class LoginAppService{

	@Autowired
	private LoginDAO loginDAO;

	public Usuario loga(String conta, String senha){
		return loginDAO.loga(conta, senha);
	}

}