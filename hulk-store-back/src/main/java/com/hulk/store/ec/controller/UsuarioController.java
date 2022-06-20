package com.hulk.store.ec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hulk.store.ec.dao.AdminDao;
import com.hulk.store.ec.model.Usuario;
import com.hulk.store.ec.util.AES;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@RequestMapping("usuario")
@Slf4j
public class UsuarioController {

	@Autowired
	private AdminDao adminDao;
	@Value( "${datainfo.key}" )
	private String semilla;
	
	@GetMapping("/usuario")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<Usuario> obtenerInformacionUsuario(@RequestParam("id") String id,
			@RequestParam MultiValueMap<String, String> params){
		try {
			Integer usuId =Integer.parseInt(AES.decrypt(id, semilla));
			
			Usuario usuario = (Usuario) adminDao.entidadPorId(null, usuId, Usuario.class);
			
			return ResponseEntity.ok(usuario);
		} catch (Exception e) {
			log.error("Error al recuperar información de usuario", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
		}
	}
}
