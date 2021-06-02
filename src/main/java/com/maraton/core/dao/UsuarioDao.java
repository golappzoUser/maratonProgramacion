package com.maraton.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maraton.core.entity.Usuario;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario, Long> {

	public Usuario findByUsername(String username);

	@Query(value = "SELECT u FROM Usuario u WHERE u.rol != 'ADMIN' ORDER BY u.puntuacion DESC")
	public List<Usuario> finAllUserRol();

}
