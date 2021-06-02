package com.maraton.core.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.maraton.core.entity.Usuario;
import com.maraton.core.entity.UsuarioDesafio;

public interface FileStorageService {

	public String uploadFile(MultipartFile multipartFile, Long idUsuarioDesafio, Usuario currentUsuario);
	
	public HttpServletResponse downLoadFile(HttpServletResponse response, String fileName);
	
	public void deleteFile(UsuarioDesafio usuarioDesafio);
	
}
