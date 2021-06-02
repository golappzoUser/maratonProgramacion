package com.maraton.core.enums;

import lombok.Getter;

@Getter
public enum RolEnum {

	USER("USER","Usuario"),
	ADMIN("ADMIN","Administrador");
	
	private String nombre;
	private String descripcion;
	
	private RolEnum (String nombre, String descripcion) {
		this.nombre =  nombre;
		this.descripcion = descripcion;
	}
	
}
