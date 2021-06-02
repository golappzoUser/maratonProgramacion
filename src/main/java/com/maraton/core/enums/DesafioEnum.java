package com.maraton.core.enums;

import lombok.Getter;

@Getter
public enum DesafioEnum {

	SumaMultiplicacion("sumaMultiplicacion", "suma Multiplicación"),
	HijosContar("HijosContar", "Hijos Contar"),
	NumeroPerfecto("numeroPerfecto", "número Perfecto"),
	NumerosPares("numerosPares", "números pares"),
	Piramide("Piramide", "pirámide"),
	Secuencia("Secuencia", "Secuencia"),
	ValidarMitad("ValidarMitad", "Validar Mitad");

	private String nombre;
	private String tituloPage;

	private DesafioEnum(String nombre, String tituloPage) {
		this.nombre = nombre;
		this.tituloPage = tituloPage;
	}
	
}
