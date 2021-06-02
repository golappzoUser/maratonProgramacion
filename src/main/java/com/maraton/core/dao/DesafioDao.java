package com.maraton.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maraton.core.entity.Desafio;

@Repository
public interface DesafioDao extends JpaRepository<Desafio, Long>{
	
	public List<Desafio> findByDescripcion(String descripcion);
	
	public List<Desafio> findByNombre(String nombre);
	
}
