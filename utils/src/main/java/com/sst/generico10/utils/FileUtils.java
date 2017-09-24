package com.sst.generico10.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.io.IOUtils;

import com.sst.generico10.utils.exceptions.GeneralAppException;

@ApplicationScoped
public class FileUtils {
	
	/**
	 * Recupera un archivo de la carpeta recursos y lo devuelve en string
	 * @param filename
	 * @return
	 * @throws GeneralAppException
	 */
	public String readFileAsStringFromResourceFolder(String filename) throws GeneralAppException {
		String result = "";

		ClassLoader classLoader = getClass().getClassLoader();
		try {
		    result = IOUtils.toString(classLoader.getResourceAsStream(filename), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new GeneralAppException("No se pudo encontrar el archivo "+filename);
		}

		return result;
	}
	

}
