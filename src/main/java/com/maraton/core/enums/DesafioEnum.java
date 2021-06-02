package com.maraton.core.enums;

import lombok.Getter;

@Getter
public enum DesafioEnum {

	SumaMultiplicacion("sumaMultiplicacion", "suma Multiplicaci�n"),
	HijosContar("HijosContar", "Hijos Contar"),
	NumeroPerfecto("numeroPerfecto", "n�mero Perfecto"),
	NumerosPares("numerosPares", "n�meros pares"),
	Piramide("Piramide", "pir�mide"),
	Secuencia("Secuencia", "Secuencia"),
	ValidarMitad("ValidarMitad", "Validar Mitad");

	private String nombre;
	private String tituloPage;

	private DesafioEnum(String nombre, String tituloPage) {
		this.nombre = nombre;
		this.tituloPage = tituloPage;
	}
	
}
