package com.sst.services.correo;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.sst.generico10.utils.exceptions.CorreoException;
import com.sst.services.appconfiguration.annotations.AdminEmail;
import com.sst.services.appconfiguration.annotations.AdminEmailPassword;
import com.sst.services.appconfiguration.annotations.AdminEmailSmtpAuth;
import com.sst.services.appconfiguration.annotations.AdminEmailSmtpHost;
import com.sst.services.appconfiguration.annotations.AdminEmailSmtpPort;
import com.sst.services.appconfiguration.annotations.AdminEmailSmtpTls;
import com.sst.services.appconfiguration.annotations.AdminEmailSmtpUser;

@Stateless
public class CorreoService {
	private static final Logger LOGGER = Logger.getLogger(CorreoService.class);

	// parametros configuracion
	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@AdminEmail
	private String adminEmail;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@AdminEmailPassword
	private String adminEmailPassword;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@AdminEmailSmtpAuth
	private boolean adminEmailSmtpAuth;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@AdminEmailSmtpHost
	private String adminEmailHost;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@AdminEmailSmtpPort
	private int adminEmailPort;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@AdminEmailSmtpTls
	private boolean adminEmailTls;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@AdminEmailSmtpUser
	private String adminEmailUser;

	@Asynchronous
	public void enviarCorreoElectronicoDeAdmin(String asunto, String mensaje, String destinatario, boolean eshtml)
			throws CorreoException {
		Session session = Session.getDefaultInstance(getSMTPPropertiesAdminEmail());
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);

		// Quien envia el correo
		try {
			message.setFrom(new InternetAddress(adminEmail));
			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));

			message.setSubject(asunto);
			if (eshtml) {
				message.setText(mensaje, StandardCharsets.UTF_8.name(),"html");
			}else {
				message.setText(mensaje);
			}

			Transport t = session.getTransport("smtp");

			t.connect(adminEmail, adminEmailPassword);

			t.sendMessage(message, message.getAllRecipients());

			t.close();

			LOGGER.debug("correo enviado");

		} catch (AddressException e) {
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new CorreoException("La dirección de destino del correo es incorrecta: " + destinatario, e);
		} catch (MessagingException e) {
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw new CorreoException("Error al eviar el correo: ", e);
		}

	}

	private Properties getSMTPPropertiesAdminEmail() {
		return getSMTPProperties(adminEmailHost, adminEmailPort, adminEmailTls, adminEmail, adminEmailSmtpAuth);
	}

	private Properties getSMTPProperties(String smtpHost, int smtpPort, Boolean smtpTls, String smtpUser,
			Boolean smtpAuth) {

		Properties props = new Properties();

		// Nombre del host de correo, es smtp.gmail.com
		props.setProperty("mail.smtp.host", smtpHost);

		LOGGER.debug("smtpTls" + smtpTls.toString());
		// TLS si está disponible
		props.setProperty("mail.smtp.starttls.enable", String.valueOf(smtpTls));

		LOGGER.debug("smtpTls" + smtpPort);
		// Puerto de gmail para envio de correos
		props.setProperty("mail.smtp.port", String.valueOf(smtpPort));

		// Nombre del usuario
		props.setProperty("mail.smtp.user", smtpUser);

		// Si requiere o no usuario y password para conectarse.
		props.setProperty("mail.smtp.auth", String.valueOf(smtpAuth));

		return props;
	}

}
