package dao;

import modelo.Usuario;

public interface LoginDAO {
	Usuario loga(String conta, String senha);
}