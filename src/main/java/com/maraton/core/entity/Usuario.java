package com.maraton.core.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
@Entity(name = "Usuario")
public class Usuario implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El nombre no debe ser vacio")
	@Size(min = 3)
	private String nombre;
	
	@NotEmpty(message = "El apellido no debe ser vacio")
	@Size(min = 3)
	private String apellido;
	
	@NotEmpty(message = "El correo no debe ser vacio")
	@Email(message = "La estructura del Email no es la correpta")
	private String email;
	private String rol;
	
	@NotEmpty(message = "El username no debe ser vacio")
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	
	@NotEmpty(message = "La contraseña no debe ser vacio")
	private String password;
	private boolean habilitado;
	private int puntuacion;
	
	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private List<UsuarioDesafio> usuarioDesafio;
	
	@PrePersist
	public void prePersist() {this.fechaCreacion = new Date();}
	
	//no lo registra en la base de datos
	@Transient
	private List<UsuarioDesafio> UsuarioDesafio;
	
}
