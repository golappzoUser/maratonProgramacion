package com.maraton.core.enums;

import lombok.Getter;

@Getter
public enum DesafiosEstadosEnum {

	Aceptado("Aceptado","Aceptado","A"),
	Pendiente("Pendiente","Pendiente","P"),
	Rechazado("Rechazado","Rechazado","R"),
	NoEnvio("NoEnvio","No se ha Enviado","N");
	
	private String nombre;
	private String descripcion;
	private String notacion;
	
	private DesafiosEstadosEnum (String nombre, String descripcion, String notacion) {
		this.nombre =  nombre;
		this.descripcion = descripcion;
		this.notacion = notacion;
	}
	
}
