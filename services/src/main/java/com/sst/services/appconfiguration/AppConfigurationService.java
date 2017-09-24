package com.sst.services.appconfiguration;

import java.util.EnumMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.sst.generico10.dao.appconfig.AppConfigurationDao;
import com.sst.generico10.model.appconfiguration.AppConfiguration;
import com.sst.generico10.model.appconfiguration.AppConfigurationKey;
import com.sst.generico10.utils.DataTypeUtils;
import com.sst.generico10.utils.exceptions.GeneralAppException;
import com.sst.generico10.utils.exceptions.GeneralAppRuntimeException;
import com.sst.services.appconfiguration.annotations.AdminEmail;
import com.sst.services.appconfiguration.annotations.AdminEmailPassword;
import com.sst.services.appconfiguration.annotations.AdminEmailSmtpAuth;
import com.sst.services.appconfiguration.annotations.AdminEmailSmtpHost;
import com.sst.services.appconfiguration.annotations.AdminEmailSmtpPort;
import com.sst.services.appconfiguration.annotations.AdminEmailSmtpTls;
import com.sst.services.appconfiguration.annotations.AdminEmailSmtpUser;
import com.sst.services.appconfiguration.annotations.AppUrl;
import com.sst.services.appconfiguration.annotations.PasswordResetEmailTime;

@Singleton
@DependsOn("AppDataConfig")
public class AppConfigurationService {

	private static final Logger LOGGER = Logger.getLogger(AppConfigurationService.class);

	@Inject
	AppConfigurationDao appConfigurationDao;

	@Inject
	DataTypeUtils dataTypeUtils;

	private EnumMap<AppConfigurationKey, AppConfiguration> appConfigurationCache = new EnumMap<>(
			AppConfigurationKey.class);

	@PostConstruct
	public void init() {

		llenarAppConfigurationCache();

	}

	@Produces
	@AdminEmail
	public String getAdminEmail() {
		AppConfiguration appConf = appConfigurationCache.get(AppConfigurationKey.ADMIN_EMAIL);
		if (StringUtils.isBlank(appConf.getValor())) {
			throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
					AppConfigurationKey.ADMIN_EMAIL, appConf.getValor()));
		} else {
			return appConf.getValor();
		}
	}

	@Produces
	@AdminEmailPassword
	public String getAdminEmailPassword() {
		AppConfiguration appConf = appConfigurationCache.get(AppConfigurationKey.ADMIN_EMAIL_PASSWORD);
		if (StringUtils.isBlank(appConf.getValor())) {
			throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
					AppConfigurationKey.ADMIN_EMAIL_PASSWORD, appConf.getValor()));
		} else {
			return appConf.getValor();
		}
	}

	@Produces
	@AdminEmailSmtpAuth
	public boolean getAdminEmailSmtpAutentication() {
		AppConfiguration appConf = appConfigurationCache.get(AppConfigurationKey.ADMIN_EMAIL_SMTP_AUTH);
		if (StringUtils.isBlank(appConf.getValor())) {
			throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
					AppConfigurationKey.ADMIN_EMAIL_SMTP_AUTH, appConf.getValor()));
		} else {

			try {
				return dataTypeUtils.stringToBoolean(appConf.getValor());
			} catch (GeneralAppException e) {
				LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
				LOGGER.error(ExceptionUtils.getStackTrace(e));
				throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
						AppConfigurationKey.ADMIN_EMAIL_SMTP_AUTH, appConf.getValor()),e);
			}
		}
	}

	@Produces
	@AdminEmailSmtpHost
	public String getAdminEmailSmtpHost() {
		AppConfiguration appConf = appConfigurationCache.get(AppConfigurationKey.ADMIN_EMAIL_SMTP_HOST);
		if (StringUtils.isBlank(appConf.getValor())) {
			throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
					AppConfigurationKey.ADMIN_EMAIL_SMTP_HOST, appConf.getValor()));
		} else {

			return appConf.getValor();

		}
	}

	@Produces
	@AdminEmailSmtpPort
	public int getAdminEmailSmtpPort() {
		AppConfiguration appConf = appConfigurationCache.get(AppConfigurationKey.ADMIN_EMAIL_SMTP_PORT);
		if (StringUtils.isBlank(appConf.getValor())) {
			throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
					AppConfigurationKey.ADMIN_EMAIL_SMTP_PORT, appConf.getValor()));
		} else {

			try {
				return dataTypeUtils.stringToInteger(appConf.getValor());
			} catch (GeneralAppException e) {
				LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
				LOGGER.error(ExceptionUtils.getStackTrace(e));
				throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
						AppConfigurationKey.ADMIN_EMAIL_SMTP_PORT, appConf.getValor()), e);
			}

		}
	}

	@Produces
	@AdminEmailSmtpTls
	public boolean getAdminEmailSmtpTls() {
		AppConfiguration appConf = appConfigurationCache.get(AppConfigurationKey.ADMIN_EMAIL_SMTP_TLS);
		if (StringUtils.isBlank(appConf.getValor())) {
			throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
					AppConfigurationKey.ADMIN_EMAIL_SMTP_TLS, appConf.getValor()));
		} else {

			try {
				return dataTypeUtils.stringToBoolean(appConf.getValor());
			} catch (GeneralAppException e) {
				LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
				LOGGER.error(ExceptionUtils.getStackTrace(e));
				throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
						AppConfigurationKey.ADMIN_EMAIL_SMTP_TLS, appConf.getValor()), e);
			}
		}
	}

	@Produces
	@AdminEmailSmtpUser
	public String getAdminEmailSmtpUser() {
		AppConfiguration appConf = appConfigurationCache.get(AppConfigurationKey.ADMIN_EMAIL_SMTP_USER);
		if (StringUtils.isBlank(appConf.getValor())) {
			throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
					AppConfigurationKey.ADMIN_EMAIL_SMTP_USER, appConf.getValor()));
		} else {

			return appConf.getValor();

		}
	}
	
	@Produces
	@AppUrl
	public String getAppUrl() {
		AppConfiguration appConf = appConfigurationCache.get(AppConfigurationKey.APP_URL);
		if (StringUtils.isBlank(appConf.getValor())) {
			throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
					AppConfigurationKey.APP_URL, appConf.getValor()));
		} else {

			return appConf.getValor();

		}
	}

	@Produces
	@PasswordResetEmailTime
	public int getPasswordResetEmailTime() {
		AppConfiguration appConf = appConfigurationCache.get(AppConfigurationKey.USER_PASSWORD_RESET_TIME);
		if (StringUtils.isBlank(appConf.getValor())) {
			throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
					AppConfigurationKey.USER_PASSWORD_RESET_TIME, appConf.getValor()));
		} else {

			try {
				return dataTypeUtils.stringToInteger(appConf.getValor());
			} catch (GeneralAppException e) {
				LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
				LOGGER.error(ExceptionUtils.getStackTrace(e));
				throw new GeneralAppRuntimeException(crearMensajeProblemaValorConfiguracion(
						AppConfigurationKey.USER_PASSWORD_RESET_TIME, appConf.getValor()), e);
			}

		}
	}

	private String crearMensajeProblemaValorConfiguracion(AppConfigurationKey clave, String valor) {
		StringBuilder sb = new StringBuilder();
		sb.append("El valor de configuración ");
		sb.append(clave);
		sb.append(" de la aplicación no es correcto. (" + valor + ")");
		return sb.toString();

	}

	private void llenarAppConfigurationCache() {
		appConfigurationCache.clear();

		List<AppConfiguration> appConfs = appConfigurationDao.findAll();
		if (CollectionUtils.isNotEmpty(appConfs)) {
			for (AppConfiguration appConfiguration : appConfs) {
				appConfigurationCache.put(appConfiguration.getClave(), appConfiguration);
			}
		}
	}

	public String findValorByClave(AppConfigurationKey clave) {
		AppConfiguration appconfig = appConfigurationCache.get(clave);
		if (appconfig == null) {
			return null;
		} else {
			return appconfig.getValor();
		}
	}

	public AppConfiguration findByClave(AppConfigurationKey clave) {
		return appConfigurationCache.get(clave);
	}

}
