package com.maraton.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maraton.core.entity.view.Competidor;
import com.maraton.core.service.CompetidorService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class UsuarioDesafioTest {

	@Autowired
	private CompetidorService competidorService;
	
	@Test
	public void finByUsuarioTest() {
		
		log.info("inicia finByUsuarioTest");
		
		List<Competidor> competidores = competidorService.createCompetidoresIndex();
		
		assertEquals(7, competidores.size());
	}
	
}
