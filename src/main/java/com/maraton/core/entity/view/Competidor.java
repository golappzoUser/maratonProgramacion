package com.maraton.core.entity.view;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class Competidor {
	
	private String nombre;
	private String apellido;
	private int puntuacion;
	private String estadoSumaMultiplicacion;
	private String estadoHijosContar;
	private String estadoNumeroPerfecto;
	private String estadoNumerosPares;
	private String estadoPiramide;
	private String estadoSecuencia;
	private String estadoValidarMitad;
}
