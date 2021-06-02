package com.maraton.core.enums;

import lombok.Getter;

@Getter
public enum ExtencionFileEnum {

	JPG("JPG",".jpg"),
	PNG("PNG",".png"),
	TXT("TXT",".txt");
	
	private String nombre;
	private String extencion;
	
	private ExtencionFileEnum (String nombre, String extencion) {
		this.nombre =  nombre;
		this.extencion = extencion;
	}
	
}
