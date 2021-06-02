package com.maraton.core.service;

import java.util.List;

import com.maraton.core.entity.Desafio;
import com.maraton.core.entity.UsuarioDesafio;

public interface DesafioService {
	
	public List<Desafio> finAllDesafios();
	
	public List<Desafio> findDesafiosByUsuario(List<UsuarioDesafio> usuarioDesafios);
	
}
