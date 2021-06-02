package com.maraton.core.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maraton.core.dao.DesafioDao;
import com.maraton.core.dao.UsuarioDesafioDao;
import com.maraton.core.entity.Desafio;
import com.maraton.core.entity.Usuario;
import com.maraton.core.entity.UsuarioDesafio;
import com.maraton.core.enums.DesafiosEstadosEnum;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UsuarioDesafioServiceImpl implements UsuarioDesafioService {

	@Autowired
	private UsuarioDesafioDao usuarioDesafioDao;

	@Autowired
	private DesafioDao desafioDao;

	@Override
	public void saveDesafiosByUsuario(Usuario usuario) {
		try {
			List<Desafio> desafios = desafioDao.findAll();

			for (Desafio desafio : desafios) {
				UsuarioDesafio usuarioDesafio = new UsuarioDesafio(null, usuario, desafio,
						DesafiosEstadosEnum.NoEnvio.getNombre(), null, new Date());
				usuarioDesafioDao.save(usuarioDesafio);
			}

			log.info("Se generaron los desafios para el usuario id: " + usuario.getId());

		} catch (Exception e) {
			log.error("Error al generar los desafios para el usuario id: " + usuario.getId()+" Error: "+e);
			new IOException(e);
		}
	}

	@Override
	public void deleteAll() {
		try {
			usuarioDesafioDao.deleteAll();
			log.info("Se eliminaron todos los retos asociados a los usuarios");
		} catch (Exception e) {
			log.error("No se eliminaron los retos asociados a los usuarios, error: " + e);
			new IOException(e);
		}
	}

	@Override
	public List<UsuarioDesafio> finByUsuario(Long usuario) {
		return usuarioDesafioDao.finByUsuario(usuario);
	}

	@Override
	public List<UsuarioDesafio> finByUsuarioNotEstado(Long usuario, String estado) {
		return usuarioDesafioDao.finByUsuarioNotEstado(usuario, estado);
	}

	@Override
	public List<UsuarioDesafio> findUsuarioDesafioByEstado(String estado) {
		return usuarioDesafioDao.findUsuarioDesafioByEstado(estado);
	}

	@Override
	public UsuarioDesafio findById(Long id) {
		return usuarioDesafioDao.getById(id);
	}

	@Override
	public void updateEstadoDescripcionToDesafio(Long id, String estado, String decripcion) {
		try {
			UsuarioDesafio usuarioDesafio = usuarioDesafioDao.getById(id);

			usuarioDesafio.setEstado(estado);
			usuarioDesafio.setDescripcion(decripcion);
			usuarioDesafio.setFechaModificacion(new Date());

			usuarioDesafioDao.save(usuarioDesafio);
			log.info("el estado ha sido actulizado, usuarioDesafio id: " + id);
		} catch (Exception e) {
			log.error("No se logro actulizar el estado del usuarioDesafio id: " + id+" Error: "+e);
			new IOException(e);
		}

	}

}
