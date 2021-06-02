package com.maraton.core.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "desafios")
@Entity(name = "Desafio")
public class Desafio implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String descripcion;
	private String titulo;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "desafio")
    private List<UsuarioDesafio> usuarioDesafio;
	
	//no lo registra en la base de datos
	@Transient
	private String notacionEstado;
	@Transient
	private String imagenSolucion;
	@Transient
	private String estado;
	@Transient
	private String nombreArchivo;
	@Transient
	private String descripcionEstado;
	
}
