package com.hulk.store.ec.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulk.store.ec.dao.AdminDao;
import com.hulk.store.ec.dto.DtoPaginacion;
import com.hulk.store.ec.dto.Filtro;

/**
 * Paginador genérico
 * 
 * @author edwin
 *
 * @param <T>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Paginacion<T> {
	private int pageSize;
	private int page;
	private AdminDao adminSrv;
	private Map<String, Object> filter;
	private Object[][] filtro;
	private String colOrder;
	private boolean order;
	private Class<T> clazz;
	private List<T> lista;
	private long count;
	private String columns;
	private int first;

	/**
	 * Construcción inicio de instancias heredadas, filtros según reglas o
	 * ingresados por el usuario, ordenaciones ...
	 * 
	 * @param judicialSrv Servicio EJB
	 * @param pageSize    Tamaño listado
	 * @param clazz       Type class model
	 * @param colOrder    Columna a a ordenar
	 * @param filtro      condiciones propias
	 * @param columns     Select filed1, field2 ...
	 * @param params      parámetros generales
	 * @param filtrar     Si se procede a desrializar desde aquí
	 * @param esAdmin     si es datasource Admin
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	public Paginacion(AdminDao adminSrv, int pageSize, Class<T> clazz, String colOrder, Object[][] filtro,
			MultiValueMap<String, String> params, String columns) throws JsonMappingException, JsonProcessingException {
		this.pageSize = pageSize;
		this.adminSrv = adminSrv;
		Integer torder = Integer.parseInt(params.getFirst("sortOrder") == null ? "1" : params.getFirst("sortOrder"));
		String cconstr = params.getFirst("filter") == null ? "{}" : params.getFirst("filter");
		this.filter = new HashMap<>();
		Map<String, Filtro> filtros = new ObjectMapper().readValue(cconstr,
				new TypeReference<HashMap<String, Filtro>>() {
				});
		for (Iterator<String> it = filtros.keySet().iterator(); it.hasNext();) {
			String column = it.next();
			Filtro flt = (Filtro) filtros.get(column);
			boolean transiend = false;
			if (this.adminSrv.getColtrasient() != null && this.adminSrv.getColtrasient().containsKey(column)) {
				column = this.adminSrv.getColtrasient().get(column).toString();
				transiend = true;
			}

			if (flt.getValue() != null) {
				filter.put(!transiend ? this.attributeDes(column, clazz) : column,
						!transiend ? flt.getValue() : flt.getValue());
			}
		}
		this.order = torder.equals(1);
		this.clazz = clazz;
		this.colOrder = colOrder;
		if (params.getFirst("sortField") != null && !params.getFirst("sortField").equals("undefined")) {
			this.colOrder = this.attributeDes(params.getFirst("sortField"), clazz);
		}
		this.filtro = filtro;
		this.lista = new ArrayList<>();
		this.count = params.getFirst("total") == null? -1 : Long.parseLong(params.getFirst("total"));
		this.columns = columns;
		this.first = params.getFirst("pageNumber") == null ? 0 : Integer.parseInt(params.getFirst("pageNumber"));
	}

	/**
	 * Retorna valores de paginación
	 * 
	 * @return DtoPaginacion
	 */
	public DtoPaginacion valores() {
		DtoPaginacion dtoPaginacion = new DtoPaginacion();
		dtoPaginacion.setCount(this.getItemsCount());
		dtoPaginacion.setLista(this.listar());

		return dtoPaginacion;
	}

	/**
	 * Total registros
	 * 
	 * @return
	 */
	public long getItemsCount() {
		if (count < 0) {
			count = adminSrv.contarRegistros(filter, filtro, clazz);
		}
		return count;
	}

	/**
	 * Registros
	 * 
	 * @return Lista
	 */
	public List listar() {
		lista.clear();
		if (count > 0) {
			lista.addAll(adminSrv.listaRegistro(clazz, this.first, pageSize, colOrder, order, filter, filtro, columns));
		}
		return lista;
	}

	/**
	 * Attribute name from Json Annotation
	 * 
	 * @param name
	 * @param clazz
	 * @return String attribute name
	 */
	public String attributeDes(String name, Class<T> clazz) {
		Field[] fileds = clazz.getDeclaredFields();
		Field field = null;
		for (int i = 0; i < fileds.length; i++) {
			Annotation ann[] = fileds[i].getDeclaredAnnotations();
			for (int j = 0; j < ann.length; j++) {
				if (ann[j] instanceof JsonProperty && ((JsonProperty) ann[j]).value().equals(name)) {
					field = fileds[i];
					break;
				}
			}
			if (field != null) {
				break;
			}
		}

		return field == null ? null : field.getName();
	}

	public Map<String, Object> getFilter() {
		return filter;
	}

	public void setFilter(Map<String, Object> filter) {
		this.filter = filter;
	}

	public Object[][] getFiltro() {
		return filtro;
	}

	public void setFiltro(Object[][] filtro) {
		this.filtro = filtro;
	}

	public String getColOrder() {
		return colOrder;
	}

	public void setColOrder(String colOrder) {
		this.colOrder = colOrder;
	}

	public boolean isOrder() {
		return order;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}

	public List<T> getLista() {
		return lista;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public Paginacion(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageFirstItem() {
		return page * pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}
}
