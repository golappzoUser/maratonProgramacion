package com.maraton.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maraton.core.entity.UsuarioDesafio;

@Repository
public interface UsuarioDesafioDao extends JpaRepository<UsuarioDesafio, Long> {

	@Query(value="SELECT d FROM UsuarioDesafio d WHERE d.usuario IN "
			+ "( SELECT u.id FROM Usuario u WHERE u.id = ?1 ORDER BY u.puntuacion DESC)")
	public List<UsuarioDesafio> finByUsuario(Long usuario);	
	
	@Query(value="SELECT d FROM UsuarioDesafio d WHERE d.estado != ?2 AND d.usuario IN "
			+ "( SELECT u.id FROM Usuario u WHERE u.id = ?1 ORDER BY u.puntuacion DESC)")
	public List<UsuarioDesafio> finByUsuarioNotEstado(Long usuario, String estado);

	@Query(value="SELECT d FROM UsuarioDesafio d WHERE d.estado = ?1 ORDER BY d.fechaModificacion ASC")
	public List<UsuarioDesafio> findUsuarioDesafioByEstado(String estado);
	
}
