package com.maraton.core.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.maraton.core.entity.Usuario;
import com.maraton.core.service.UsuarioDesafioService;
import com.maraton.core.service.UsuarioService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class LoginController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioDesafioService usuarioDesafioService;

	@GetMapping("/auth/login")
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());

		return "formularios/loginForm";
	}

	@GetMapping("/auth/registro")
	public String registroForm(Model model) {
		model.addAttribute("usuario", new Usuario());

		return "formularios/registroForm";
	}

	@PostMapping("/auth/registro")
	public String registro(@Valid Usuario usuario, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "registroForm";
		} else {
			Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
			
			usuarioDesafioService.saveDesafiosByUsuario(nuevoUsuario);
			
			model.addAttribute("usuario", nuevoUsuario);
			log.info("Finaliza creacion de usuario");
		}

		return "redirect:/auth/login";
	}

}
