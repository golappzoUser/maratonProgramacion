package com.maraton.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.maraton.core.dao.UsuarioDao;
import com.maraton.core.entity.Usuario;
import com.maraton.core.enums.RolEnum;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class UsuarioTest {

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	public void CreateAdminUser() {

		String username = "admin2"; // indica el usuario de ingreso
		String nombre = "Administrador2";
		String password = passwordEncoder.encode("admin");

		Usuario usuarioInsertado = null;

		try {

			Usuario adminUser = new Usuario(null, nombre, "admin", "admin@gmail.com", RolEnum.ADMIN.getNombre(),
					username, password, true, 0, new Date(), null, null);

			usuarioInsertado = usuarioDao.save(adminUser);
			log.info("usuario admin creado, id: "+usuarioInsertado.getId());
			
		} catch (Exception e) {
			log.error("Error creando el usuario admin");
		}

		assertEquals(nombre, usuarioInsertado.getNombre());

	}

}
