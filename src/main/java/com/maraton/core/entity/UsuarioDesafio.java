package com.maraton.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios_desafios")
@Entity(name = "UsuarioDesafio")
public class UsuarioDesafio implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

	@ManyToOne
    @JoinColumn(name = "desafio_id")
    private Desafio desafio;
    
    private String estado;
    private String descripcion;
    
	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
	private Date fechaModificacion;
	
	@PrePersist
	public void prePersist() {this.fechaModificacion = new Date();}
	
}
