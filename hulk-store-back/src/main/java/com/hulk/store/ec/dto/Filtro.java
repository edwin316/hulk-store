package com.hulk.store.ec.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * Dto
 * 
 * @author edwin
 *
 */
@JsonInclude(Include.NON_NULL)
@Getter @Setter
public class Filtro {
	private String value;
	private String matchMode;
}
