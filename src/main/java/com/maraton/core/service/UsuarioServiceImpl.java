package com.maraton.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.maraton.core.dao.UsuarioDao;
import com.maraton.core.entity.Usuario;
import com.maraton.core.enums.RolEnum;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	public Usuario finByUsername(String username) {
		return usuarioDao.findByUsername(username);
	}

	@Override
	public Usuario registrarUsuario(Usuario usuario) {
		Usuario newUsuario = new Usuario();
		try {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			usuario.setRol(RolEnum.USER.getNombre());
			usuario.setHabilitado(true);
			usuario.setPuntuacion(0);

			newUsuario = usuarioDao.save(usuario);
			log.info("Nuevo usuario almacenado con exito, id: " + newUsuario.getId());

		} catch (Exception e) {
			log.error("No se logro almacenar el usuario " + usuario.getNombre() + " Error: " + e);
		}
		return newUsuario;
	}

	@Override
	public List<Usuario> finAllOrderUsuarios() {
		return usuarioDao.findAll(Sort.by("puntuacion").descending());
	}

	@Override
	public void deleteAllUseRol() {
		try {

			List<Usuario> usuariosRolUser = usuarioDao.finAllUserRol();

			usuarioDao.deleteAll(usuariosRolUser);

			log.info("Usuarios eliminados");
		} catch (Exception e) {
			log.error("No se logro eliminar los usuarios Error: " + e);
		}
	}

	@Override
	public void updateAllHabilitado(boolean habilitado) {

		try {

			List<Usuario> usuariosRolUser = usuarioDao.finAllUserRol();

			for (Usuario usuario : usuariosRolUser) {
				usuario.setHabilitado(habilitado);
				usuarioDao.save(usuario);
			}

			log.info("Usuarios modificados, estado: " + habilitado);
		} catch (Exception e) {
			log.error("No se logro actulizar los usuarios, estado: " + habilitado + " Error: " + e);
		}

	}

	@Override
	public Usuario updatePuntosUsuario(Usuario usuario) {

		int puntos = usuario.getPuntuacion() + 10;

		log.info(String.format("Puntuacion inicial: %s, nueva puntuacion: %s del usuario id: %s",
				usuario.getPuntuacion(), puntos, usuario.getId()));

		usuario.setPuntuacion(puntos);

		return usuarioDao.save(usuario);
	}

}
