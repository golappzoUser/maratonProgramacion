package com.maraton.core.service;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maraton.core.entity.Usuario;
import com.maraton.core.entity.UsuarioDesafio;
import com.maraton.core.enums.ExtencionFileEnum;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class FileStorageServiceImpl implements FileStorageService {

	private String folderName = ".//src//main//resources//files//";

	@Override
	public String uploadFile(MultipartFile file, Long idUsuarioDesafio, Usuario currentUsuario) {
		String mensaje = null;

		if (file.isEmpty()) {
			mensaje = "El archivo esta vacio o la extencion es erronea";
			log.info(mensaje);
		} else {

			try {

				String nameFile = currentUsuario.getUsername().concat("-").concat(idUsuarioDesafio.toString())
						.concat(ExtencionFileEnum.TXT.getExtencion());

				log.info("Tamaño dle archivo: " + file.getSize());
				byte[] bytes = file.getBytes();
				Path path = Paths.get(folderName.concat(nameFile));
				Files.write(path, bytes);

				mensaje = "Ejercicio pendiente por revision";

			} catch (Exception e) {
				mensaje = "Error cargando el archivo";
				log.info(mensaje + ", error: " + e);
			}

		}

		return mensaje;
	}

	@Override
	public void deleteFile(UsuarioDesafio usuarioDesafio) {

		String nameFile = usuarioDesafio.getUsuario().getUsername().concat("-")
				.concat(usuarioDesafio.getId().toString()).concat(ExtencionFileEnum.TXT.getExtencion());
		try {

			File file = new File(folderName.concat(nameFile));
			file.delete();
			log.info("arvhico " + nameFile + " eliminado.");
		} catch (Exception e) {

			log.error("error eliminando el archivo " + nameFile + ", error: " + e);
		}
	}

	@Override
	public HttpServletResponse downLoadFile(HttpServletResponse response, String fileName) {

		int tamaño = 0;
		byte[] buf = new byte[1024];

		try {
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			FileInputStream fis = new FileInputStream(folderName + fileName);
			while ((tamaño = fis.read(buf)) > 0) {
				bos.write(buf, 0, tamaño);
			}
			bos.close();
			fis.close();
		} catch (Exception e) {
			log.error("Error descargando el archivo " + fileName + " Error: " + e);
		}

		return response;
	}

}
