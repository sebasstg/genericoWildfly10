package com.sst.generico10.utils;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;

import com.sst.generico10.utils.exceptions.GeneralAppException;

@ApplicationScoped
public class DataTypeUtils {
	
	/**
	 * Devuleve un valor booleano del estring
	 * @param booleanValue
	 * @return  null si booleanValue es null o ""
	 * true si booleanValue es si, verdadero, yes, true
	 * false si booleanValue es no, falso, not, false
	 * @throws GeneralAppException  si no es un valor vàlido
	 */
	public Boolean stringToBoolean(String booleanValue) throws GeneralAppException {
		if(StringUtils.isBlank(booleanValue)) {
			throw new GeneralAppException("El valor "+booleanValue+"no puede ser convertido a Booleano");
		}else if(booleanValue.equalsIgnoreCase("true")
				|| booleanValue.equalsIgnoreCase("yes")
				|| booleanValue.equalsIgnoreCase("si")
				|| booleanValue.equalsIgnoreCase("verdadero")
				) {
			return Boolean.TRUE;
		}else if(booleanValue.equalsIgnoreCase("false")
				|| booleanValue.equalsIgnoreCase("not")
				|| booleanValue.equalsIgnoreCase("no")
				|| booleanValue.equalsIgnoreCase("falso")
				) {
			return Boolean.FALSE;
		}else {
			throw new GeneralAppException("El valor "+booleanValue+"no puede ser convertido a Booleano");
			
		}
	}
	
	/**
	 * Convierte un String a Integer
	 * @param intValue
	 * @return valor entero con signo, null si es una cadena null o vacía
	 * @throws GeneralAppException si el valor no se puede convertir a entero
	 */
	public Integer stringToInteger(String intValue) throws GeneralAppException {
		if(StringUtils.isBlank(intValue)) {
			return null;
		}else {
			try {
				return Integer.parseInt(intValue);
			} catch (NumberFormatException e) {
				throw new GeneralAppException("El valor "+intValue+"no puede ser convertido a Integer", e);
			}
		} 	
	}
	
	

}
