package com.maraton.core.service;

import java.util.List;

import com.maraton.core.entity.Usuario;
import com.maraton.core.entity.UsuarioDesafio;

public interface UsuarioDesafioService {

	public void saveDesafiosByUsuario(Usuario usuario);

	public List<UsuarioDesafio> finByUsuario(Long usuario);

	public List<UsuarioDesafio> finByUsuarioNotEstado(Long usuario, String estado);

	public void updateEstadoDescripcionToDesafio(Long id, String estado, String decripcion);

	public UsuarioDesafio findById(Long id);

	public List<UsuarioDesafio> findUsuarioDesafioByEstado(String estado);
	
	public void deleteAll();

}
