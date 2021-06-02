package com.maraton.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.maraton.core.entity.Desafio;
import com.maraton.core.entity.Usuario;
import com.maraton.core.entity.UsuarioDesafio;
import com.maraton.core.entity.view.Competidor;
import com.maraton.core.enums.DesafiosEstadosEnum;
import com.maraton.core.service.CompetidorService;
import com.maraton.core.service.DesafioService;
import com.maraton.core.service.FileStorageService;
import com.maraton.core.service.UsuarioDesafioService;
import com.maraton.core.service.UsuarioService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/private")
public class PrivateController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CompetidorService competidorService;

	@Autowired
	private DesafioService desafioService;

	@Autowired
	private UsuarioDesafioService usuarioDesafioService;

	@Autowired
	private FileStorageService fileStorageService;

	@GetMapping("/index")
	public String index(Authentication auth, HttpSession session, Model model) {

		String username = auth.getName();

		if (session.getAttribute("usuario") == null) {
			Usuario usuario = usuarioService.finByUsername(username);
			usuario.setPassword(null);

			if (!usuario.isHabilitado()) {
				auth = null;
				session = null;
				usuario = null;
				return "redirect:/logout";
			}

			List<Competidor> competidores = competidorService.createCompetidoresIndex();

			session.setAttribute("usuario", usuario);
			model.addAttribute("competidores", competidores);

		}

		return "vistas/principalView";
	}

	@GetMapping("/recargarIndex")
	public String recargarPrincipal(HttpSession session, Model model) {

		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Usuario currentUsuario = usuarioService.finByUsername(usuario.getUsername());

		if (!currentUsuario.isHabilitado()) {
			session = null;
			currentUsuario = null;
			usuario = null;
			return "redirect:/logout";
		}

		session.setAttribute("competidores", null);

		List<Competidor> competidores = competidorService.createCompetidoresIndex();

		model.addAttribute("competidores", competidores);

		return "vistas/principalView";
	}

	@GetMapping("/desafios")
	public String getDesafios(HttpSession session, Model model) {

		List<Desafio> desafios = null;
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Usuario currentUsuario = usuarioService.finByUsername(usuario.getUsername());

		if (!currentUsuario.isHabilitado()) {
			session = null;
			currentUsuario = null;
			usuario = null;
			return "redirect:/logout";
		}

		log.info("Se obitiene el usuario id: " + currentUsuario.getId());

		List<UsuarioDesafio> usuarioDesafio = usuarioDesafioService.finByUsuarioNotEstado(currentUsuario.getId(),
				DesafiosEstadosEnum.valueOf("Aceptado").getNombre());

		if (!usuarioDesafio.isEmpty())
			desafios = desafioService.findDesafiosByUsuario(usuarioDesafio);

		log.info("Se obitienen los desafios del usuario id: " + currentUsuario.getId());

		model.addAttribute("desafios", desafios);

		return "vistas/desafiosView";
	}

	@GetMapping("/calificarDesafios")
	public String calificarDesafios(HttpSession session, Model model) {

		List<Desafio> desafiosPendientes = null;
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Usuario currentUsuario = usuarioService.finByUsername(usuario.getUsername());

		if (!currentUsuario.isHabilitado()) {
			session = null;
			currentUsuario = null;
			usuario = null;
			return "redirect:/logout";
		}

		List<UsuarioDesafio> usuarioDesafio = usuarioDesafioService
				.findUsuarioDesafioByEstado(DesafiosEstadosEnum.valueOf("Pendiente").getNombre());

		if (!usuarioDesafio.isEmpty())
			desafiosPendientes = desafioService.findDesafiosByUsuario(usuarioDesafio);

		log.info("Se obitienen los desafios pendientes");

		model.addAttribute("desafios", desafiosPendientes);

		return "vistas/adminView";
	}

	@PostMapping("/calificar/{id}")
	public String calificar(@PathVariable("id") Long idUsuarioDesafio, Model model, String decripcion,
			String estadoCalificacion) {

		UsuarioDesafio usuarioDesafio = usuarioDesafioService.findById(idUsuarioDesafio);

		usuarioDesafioService.updateEstadoDescripcionToDesafio(usuarioDesafio.getId(),
				DesafiosEstadosEnum.valueOf(estadoCalificacion).getNombre(), decripcion);

		if (estadoCalificacion.equals(DesafiosEstadosEnum.Aceptado.getNombre()))
			usuarioService.updatePuntosUsuario(usuarioDesafio.getUsuario());

		fileStorageService.deleteFile(usuarioDesafio);

		return "redirect:/private/calificarDesafios";
	}

	@RequestMapping("/file/{fileName}")
	@ResponseBody
	public void getFile(@PathVariable("fileName") String fileName, HttpServletResponse response) {

		response.setContentType("application/txt");
		response.setHeader("Content-Disposition", "attachment: filename=" + fileName);
		response.setHeader("Content-Tranfer-Enconding", "binary");

		try {
			fileStorageService.downLoadFile(response, fileName).flushBuffer();
		} catch (Exception e) {
			log.error("Error descargando el archivo " + fileName);
		}
	}

	@PostMapping("/iniciarMaraton")
	public String iniciarMaraton(Model model) {

		usuarioDesafioService.deleteAll();
		usuarioService.deleteAllUseRol();

		return "redirect:/private/recargarIndex";
	}

	@PostMapping("/terminarMaraton")
	public String terminarMaraton(Model model) {
		usuarioService.updateAllHabilitado(false);
		return "redirect:/private/recargarIndex";
	}

	@PostMapping("/cargarArchivo/{id}")
	public String cargarArchivo(@PathVariable("id") Long idUsuarioDesafio, HttpSession session, MultipartFile archivo,
			Model model) {

		String estadoCarga = null;

		try {
			log.info("Archivo recibido para guardar");
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			Usuario currentUsuario = usuarioService.finByUsername(usuario.getUsername());

			if (!currentUsuario.isHabilitado()) {
				session = null;
				currentUsuario = null;
				return "redirect:/logout";
			}

			estadoCarga = fileStorageService.uploadFile(archivo, idUsuarioDesafio, currentUsuario);

			usuarioDesafioService.updateEstadoDescripcionToDesafio(idUsuarioDesafio,
					DesafiosEstadosEnum.Pendiente.getNombre(), null);

		} catch (Exception e) {
			log.error("No se logro cargar el archivo");
		}

		model.addAttribute("estadoCarga", estadoCarga);

		return "redirect:/private/desafios";
	}

}
