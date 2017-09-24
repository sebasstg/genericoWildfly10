package com.sst.web.controller;

import java.io.IOException;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Calendar;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

	/**
	 * Adiciona un error a la lista de mensajes de FacesContext.
	 * 
	 * @param cuerpoMensaje
	 */
	public void adicionarError(String cuerpoMensaje) {
		adicionarMensaje(FacesMessage.SEVERITY_ERROR, cuerpoMensaje);
	}

	public void adicionarError(String resumen, String cuerpoMensaje) {
		adicionarMensaje(FacesMessage.SEVERITY_ERROR, resumen, cuerpoMensaje);
	}

	/**
	 * Adiciona un aviso informativo a la lista de mensajes de FacesContext.
	 * 
	 * @param cuerpoMensaje
	 */
	public void adicionarAvisoInfo(String cuerpoMensaje) {
		adicionarMensaje(FacesMessage.SEVERITY_INFO, cuerpoMensaje);
	}

	public void adicionarAvisoInfo(String resumen, String cuerpoMensaje) {
		adicionarMensaje(FacesMessage.SEVERITY_INFO, resumen, cuerpoMensaje);
	}

	/**
	 * Adiciona un aviso de advertencia a la lista de mensajes de FacesContext.
	 * 
	 * @param cuerpoMensaje
	 */
	public void adicionarAvisoAdvertencia(String cuerpoMensaje) {
		adicionarMensaje(FacesMessage.SEVERITY_WARN, cuerpoMensaje);
	}

	public void adicionarAvisoAdvertencia(String resumen, String cuerpoMensaje) {
		adicionarMensaje(FacesMessage.SEVERITY_WARN, resumen, cuerpoMensaje);
	}

	private void adicionarMensaje(Severity tipo, String cuerpoMensaje) {

		FacesContext.getCurrentInstance().addMessage(Calendar.getInstance().toString(),
				new FacesMessage(tipo, cuerpoMensaje, cuerpoMensaje));
		if (FacesContext.getCurrentInstance().getMessageList() != null
				&& !FacesContext.getCurrentInstance().getMessageList().isEmpty()) {
			LOGGER.info(FacesContext.getCurrentInstance().getMessageList().size() + " mensajes");
		}
	}

	private void adicionarMensaje(Severity tipo, String resumen, String cuerpoMensaje) {

		FacesContext.getCurrentInstance().addMessage(Calendar.getInstance().toString(),
				new FacesMessage(tipo, resumen, cuerpoMensaje));
		if (FacesContext.getCurrentInstance().getMessageList() != null
				&& !FacesContext.getCurrentInstance().getMessageList().isEmpty()) {
			LOGGER.info(FacesContext.getCurrentInstance().getMessageList().size() + " mensajes");
		}
	}

	/**
	 * Redirecciona hacia una p&aacute;gina.
	 * 
	 * @param paginaObjetivo
	 * @return
	 */
	public void redirecionar(String paginaObjetivo) {

		FacesContext contexto = FacesContext.getCurrentInstance();
		ExternalContext contextoExterno = contexto.getExternalContext();
		String url = contextoExterno
				.encodeActionURL(contexto.getApplication().getViewHandler().getActionURL(contexto, paginaObjetivo));

		try {
			contextoExterno.redirect(url);
		} catch (IOException ioe) {
			throw new FacesException(ioe);
		}
	}

	/**
	 * Obtiene la sesi&oacute;n actual.
	 * 
	 * @return la sesi&oacute;n actual
	 */
	public HttpSession obtenerSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	public Map<String, Object> getRequestMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
	}

	/**
	 * Logout de la sesi&oacute;n actual.
	 * 
	 * @return string de redirecci&oacute;n
	 */
	public String logout() {
		SecurityContextHolder.clearContext();
		return "/logout";
	}

}
