package com.maraton.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maraton.core.dao.DesafioDao;
import com.maraton.core.entity.Desafio;
import com.maraton.core.entity.UsuarioDesafio;
import com.maraton.core.enums.DesafiosEstadosEnum;
import com.maraton.core.enums.ExtencionFileEnum;

@Service
public class DesafioServiceImpl implements DesafioService {

	@Autowired
	private DesafioDao desafioDao;

	@Override
	public List<Desafio> finAllDesafios() {
		return desafioDao.findAll();
	}

	@Override
	public List<Desafio> findDesafiosByUsuario(List<UsuarioDesafio> usuarioDesafios) {
		List<Desafio> desafios = new ArrayList<>();

		for (UsuarioDesafio usuarioDesafio : usuarioDesafios) {
			Desafio newDesafio = new Desafio();
			newDesafio.setId(usuarioDesafio.getDesafio().getId());
			newDesafio.setTitulo(usuarioDesafio.getDesafio().getTitulo());
			newDesafio.setDescripcion(usuarioDesafio.getDesafio().getDescripcion());
			newDesafio.setNotacionEstado(DesafiosEstadosEnum.valueOf(usuarioDesafio.getEstado()).getNotacion());
			newDesafio.setEstado(DesafiosEstadosEnum.valueOf(usuarioDesafio.getEstado()).getNombre());
			newDesafio.setDescripcionEstado(usuarioDesafio.getDescripcion());
			newDesafio.setNombreArchivo(usuarioDesafio.getUsuario().getUsername().concat("-")
					.concat(usuarioDesafio.getId().toString()).concat(ExtencionFileEnum.TXT.getExtencion()));
			newDesafio.setImagenSolucion(
					usuarioDesafio.getDesafio().getNombre().concat(ExtencionFileEnum.PNG.getExtencion()));

			desafios.add(newDesafio);
		}

		return desafios;
	}

}
