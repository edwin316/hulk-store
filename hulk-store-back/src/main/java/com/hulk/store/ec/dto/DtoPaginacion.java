package com.hulk.store.ec.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Dto
 * 
 * @author edwin
 *
 */
@SuppressWarnings("rawtypes")
@JsonInclude(Include.NON_NULL)
public class DtoPaginacion {
	private long count;
	private List lista;
	private List<SelectItem> lista1;
	private List<SelectItem> lista2;
	private List<SelectItem> lista3;
	private List<SelectItem> lista4;
	private List<SelectItem> lista5;
	private List<SelectItem> lista6;
	private List<ISeleccionStr> lista7;
	private Object oitem;
	private Object oitem2;
	private SelectItem item;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List getLista() {
		return lista;
	}

	public void setLista(List lista) {
		this.lista = lista;
	}

	public List<SelectItem> getLista1() {
		return lista1;
	}

	public void setLista1(List<SelectItem> lista1) {
		this.lista1 = lista1;
	}

	public List<SelectItem> getLista2() {
		return lista2;
	}

	public void setLista2(List<SelectItem> lista2) {
		this.lista2 = lista2;
	}

	public List<SelectItem> getLista3() {
		return lista3;
	}

	public void setLista3(List<SelectItem> lista3) {
		this.lista3 = lista3;
	}

	public List<SelectItem> getLista4() {
		return lista4;
	}

	public void setLista4(List<SelectItem> lista4) {
		this.lista4 = lista4;
	}

	public List<SelectItem> getLista5() {
		return lista5;
	}

	public void setLista5(List<SelectItem> lista5) {
		this.lista5 = lista5;
	}

	public SelectItem getItem() {
		return item;
	}

	public void setItem(SelectItem item) {
		this.item = item;
	}

	public Object getOitem() {
		return oitem;
	}

	public void setOitem(Object oitem) {
		this.oitem = oitem;
	}

	public List<SelectItem> getLista6() {
		return lista6;
	}

	public void setLista6(List<SelectItem> lista6) {
		this.lista6 = lista6;
	}

	public List<ISeleccionStr> getLista7() {
		return lista7;
	}

	public void setLista7(List<ISeleccionStr> lista7) {
		this.lista7 = lista7;
	}

	public Object getOitem2() {
		return oitem2;
	}

	public void setOitem2(Object oitem2) {
		this.oitem2 = oitem2;
	}

	
}
