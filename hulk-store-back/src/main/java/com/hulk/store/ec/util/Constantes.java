package com.hulk.store.ec.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Constantes requeridas en el sistema
 * 
 * @author edwin
 *
 */
public class Constantes {

	public static int pageSize = 10;
	public static final String FORMAT_FECHA = "dd/MM/yyyy";
	public static final String FORMAT_FECHA_ENVIO_CORREO="yyyy-MM-dd HH:mm:sss";
	public static final String FORMAT_FECHA_BDD = "yyyy-MM-dd";
	public static final String FORMAT_FECHA_BDD_HORA = "dd/MM/yyyy HH:mm";
	public static final String TIPO_REQUERIMIENTO_RN = "6";
	public static final int LIMIT = 10;
	public static final String PRODUCTO_NEMONICO = "PRODUCTOS";

	public static String fechaActual(int opc, Integer tiempo) {
		String formato;
		switch (opc) {
		case 1:
			formato = Constantes.FORMAT_FECHA_BDD;
			break;
		case 2:
			formato = Constantes.FORMAT_FECHA_BDD_HORA;
			break;
		default:
			formato = Constantes.FORMAT_FECHA;
			break;
		}
		Calendar fecha = Calendar.getInstance();
		if (tiempo != null) {
			fecha.add(Calendar.MINUTE, tiempo);
		}
		SimpleDateFormat formater = new SimpleDateFormat(formato);

		return formater.format(fecha.getTime());
	}
}
