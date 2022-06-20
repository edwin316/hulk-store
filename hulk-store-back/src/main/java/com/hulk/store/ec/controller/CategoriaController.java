package com.hulk.store.ec.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hulk.store.ec.dao.AdminDao;
import com.hulk.store.ec.dto.SelectItem;
import com.hulk.store.ec.enums.CondicionEnum;
import com.hulk.store.ec.model.Categoria;
import com.hulk.store.ec.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@RequestMapping("categoria")
public class CategoriaController {
	
	@Autowired
	private AdminDao adminDao;

	/**
	 * Lista todas las categorias principales de productos
	 * 
	 * @param id código de usuario
	 * @param params parámetros requeridos
	 * @return llista de nodos e información adicional
	 */
	@GetMapping("/catalogo-producto-cliente")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<SelectItem>> catalogosPadreId(@PathParam("id") String id){
    	Object[][] filtro = new Object[][] { { CondicionEnum.EQUAL, "categoria.nemonico", Constantes.PRODUCTO_NEMONICO } };
        
        return ResponseEntity
                .ok(adminDao.listaCombo(filtro, Categoria.class, "catId", "nombre", true));
    } 
}
