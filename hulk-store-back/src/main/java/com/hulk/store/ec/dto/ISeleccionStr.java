package com.hulk.store.ec.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Dto
 * 
 * @author edwin
 *
 */
@JsonInclude(Include.NON_NULL)
public interface ISeleccionStr {
	public String getLabel();

	public String getValue();
}
