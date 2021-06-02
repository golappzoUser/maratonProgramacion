package com.maraton.core.service;

import java.util.List;

import com.maraton.core.entity.Usuario;

public interface UsuarioService {

	public Usuario finByUsername(String username);

	public Usuario registrarUsuario(Usuario usuario);

	public List<Usuario> finAllOrderUsuarios();

	public Usuario updatePuntosUsuario(Usuario usuario);
	
	public void updateAllHabilitado(boolean habilitado);
	
	public void deleteAllUseRol();
	
}
