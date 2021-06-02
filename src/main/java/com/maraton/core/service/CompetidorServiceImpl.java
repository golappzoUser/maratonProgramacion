package com.maraton.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maraton.core.dao.UsuarioDao;
import com.maraton.core.dao.UsuarioDesafioDao;
import com.maraton.core.entity.Usuario;
import com.maraton.core.entity.UsuarioDesafio;
import com.maraton.core.entity.view.Competidor;
import com.maraton.core.enums.DesafiosEstadosEnum;

@Service
public class CompetidorServiceImpl implements CompetidorService {

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private UsuarioDesafioDao usuarioDesafioDao;

	public List<Competidor> createCompetidoresIndex() {
		
		List<Competidor> competidoresView = new ArrayList<>();

		List<Usuario> usuarios = usuarioDao.finAllUserRol();
		for (Usuario usuario : usuarios) {
			Competidor nuevoCompetidor = new Competidor();
			nuevoCompetidor.setNombre(usuario.getNombre());
			nuevoCompetidor.setApellido(usuario.getApellido());
			nuevoCompetidor.setPuntuacion(usuario.getPuntuacion());

			List<UsuarioDesafio> usuariosDesafios = usuarioDesafioDao.finByUsuario(usuario.getId());
			for (UsuarioDesafio usuariosDesafio : usuariosDesafios) {
				int idDesafio = Integer.parseInt(usuariosDesafio.getDesafio().getId().toString());
				String estadoDesafio = usuariosDesafio.getEstado();

				switch (idDesafio) {
				case 1:
					nuevoCompetidor.setEstadoSumaMultiplicacion(DesafiosEstadosEnum.valueOf(estadoDesafio).getNotacion());
					break;
				case 2:
					nuevoCompetidor.setEstadoHijosContar(DesafiosEstadosEnum.valueOf(estadoDesafio).getNotacion());
					break;
				case 3:
					nuevoCompetidor.setEstadoNumeroPerfecto(DesafiosEstadosEnum.valueOf(estadoDesafio).getNotacion());
					break;
				case 4:
					nuevoCompetidor.setEstadoNumerosPares(DesafiosEstadosEnum.valueOf(estadoDesafio).getNotacion());
					break;
				case 5:
					nuevoCompetidor.setEstadoPiramide(DesafiosEstadosEnum.valueOf(estadoDesafio).getNotacion());
					break;
				case 6:
					nuevoCompetidor.setEstadoSecuencia(DesafiosEstadosEnum.valueOf(estadoDesafio).getNotacion());
					break;
				case 7:
					nuevoCompetidor.setEstadoValidarMitad(DesafiosEstadosEnum.valueOf(estadoDesafio).getNotacion());
					break;

				default:
					break;
				}
			}

			competidoresView.add(nuevoCompetidor);
		}

		return competidoresView;
	}

}
