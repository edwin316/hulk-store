package com.hulk.store.ec.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hulk.store.ec.dao.AdminDao;
import com.hulk.store.ec.dto.DtoPaginacion;
import com.hulk.store.ec.enums.CondicionEnum;
import com.hulk.store.ec.model.Producto;
import com.hulk.store.ec.model.Stock;
import com.hulk.store.ec.util.AES;
import com.hulk.store.ec.util.Constantes;
import com.hulk.store.ec.util.Paginacion;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import lombok.extern.slf4j.Slf4j;


@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@RequestMapping("producto")
@Slf4j
public class ProductoController {
	
	@Autowired
	private AdminDao adminDao;
	private Paginacion<Producto> paginacion;
	@Value( "${datainfo.key}" )
	private String semilla;
	
	/**
	 * Lista todos los productos en forma paginada
	 * 
	 * @param id código de usuario
	 * @param params parámetros requeridos
	 * @return llista de nodos e información adicional
	 */
	@GetMapping("/productos")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<DtoPaginacion> productos(@RequestParam("id") String id,
			@RequestParam MultiValueMap<String, String> params) {
		DtoPaginacion pag = null;
		try {
			log.error(AES.decrypt(id, semilla));
			AES.semm = semilla;
			Object[][] filtro = new Object[][] { { CondicionEnum.EQUAL, "categoria.catId", Integer.parseInt(AES.decrypt(id, semilla)) } };
			paginacion = new Paginacion(adminDao, Constantes.pageSize, Producto.class, "nombre", filtro, params,
					null);
			pag = paginacion.valores();
		} catch (Exception e) {
			log.error("Lista producto", e);
		}

		return ResponseEntity.ok(pag);
	}

	/**
	 * Actualiza según el tiempo
	 * 
	 * @param ticket información del ticket
	 * @param campo tipo de campo a actualizar
	 * @return mensaje
	 */
	@PutMapping("/generar-compra")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> generarCompra(@Validated @RequestBody Producto producto,
			BindingResult result, @RequestParam MultiValueMap<String, String> body) {
		try {
			if(body.size() != 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Parámetros inválidos, corrija la solicitud.");
			}
			if (result.hasErrors()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(result.getFieldError().getDefaultMessage());
			}
			AES.semm = semilla;
			Integer codigo = Integer.parseInt(AES.decrypt(producto.getIds(), AES.semm));
			
			Producto prodActual = (Producto) adminDao.entidadPorId(null, codigo, Producto.class);
			if(producto.getCantidad() > prodActual.getCantidad()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo realizar la compra. No hay stock");
			}
			if(!producto.getValorUnitario().equals(prodActual.getValorUnitario())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo realizar la compra. Valores unitarios no corresponden");
			}
			//actualizo la cantidad de productos
			Map<String, Object> valor = new HashMap<>();
			valor.put("cantidad", prodActual.getCantidad() - producto.getCantidad());
			Object[][] filtro = new Object[][] { { CondicionEnum.EQUAL, "proId", codigo } };
			boolean resultado = adminDao.actualizarCampos(Producto.class, filtro, valor);
			if(!resultado) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo realizar la compra. Consulte al administrador");
			}
			//Actualizo el movimiento del inventario
			filtro = new Object[][] { { CondicionEnum.EQUAL, "producto", new Producto(codigo) }, { CondicionEnum.EQUAL, "estado", true } };
			Stock stockActual = (Stock) adminDao.registroEntidad(Stock.class, filtro, null);
			Stock stock = new Stock();
			stock.setCantidad(stockActual.getCantidad() - producto.getCantidad());
			stock.setProducto(stockActual.getProducto());
			stock.setCantidadActual(stockActual.getCantidad() - producto.getCantidad());
			stock.setCantidadAnterior(stockActual.getCantidad());
			stock.setEstado(true);
			stock.setFechaEntrada(stockActual.getFechaSalida());
			stock.setFechaSalida(new Date());
			stock.setValorUnitario(stockActual.getValorUnitario());
			
			adminDao.insertar(stock);
			//Actualizar 
			valor = new HashMap<>();
			valor.put("estado", true);
			filtro = new Object[][] { { CondicionEnum.EQUAL, "stoId", stockActual.getStoId() } };
			resultado = adminDao.actualizarCampos(Stock.class, filtro, valor);			
			
			return ResponseEntity.ok("Compra realizada.");
		} catch (Exception e) {
			log.error("No se pudo realizar la compra", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo realizar la compra.");
		}
		
	}
}
