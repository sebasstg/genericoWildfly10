package com.sst.services.config;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sst.generico10.dao.appconfig.AppConfigurationDao;
import com.sst.generico10.dao.users.PermisoDao;
import com.sst.generico10.dao.users.RolDao;
import com.sst.generico10.dao.users.UserDao;
import com.sst.generico10.model.appconfiguration.AppConfiguration;
import com.sst.generico10.model.appconfiguration.AppConfigurationKey;
import com.sst.generico10.model.users.Permiso;
import com.sst.generico10.model.users.Rol;
import com.sst.generico10.model.users.Usuario;

@Singleton
@Startup
public class AppDataConfig {

	private static final Logger LOGGER = Logger.getLogger(AppDataConfig.class);

	@Inject
	UserDao userDao;

	@Inject
	AppConfigurationDao appConfigurationDao;

	@Inject
	PermisoDao permisoDao;

	@Inject
	RolDao rolDao;

	@PostConstruct
	private void init() {
		createUsers();
		createAppConfigs();

	}
	
	

	private void createAppConfigs() {
		LOGGER.info("createAppConfigs");
		
		AppConfiguration appConfigAdminEmail = new AppConfiguration("Dirección de Correo Administración",
				"Dirección de Correo de Administración del sistema, desde esta dirección se enviarán notificaciones a los usuarios",
				AppConfigurationKey.ADMIN_EMAIL, "sebassalazart@gmail.com");
		appConfigurationDao.create(appConfigAdminEmail);
		
		AppConfiguration appConfigAdminEmailPass = new AppConfiguration("Contraseña de Correo Administración",
				"Contraseña de Correo de Administración del sistema",
				AppConfigurationKey.ADMIN_EMAIL_PASSWORD, "A1a2a3a4");
		appConfigurationDao.create(appConfigAdminEmailPass);
		
		AppConfiguration appConfigAdminEmailSmtpPort = new AppConfiguration("Puerto smtp de Correo Administración",
				"Puerto smtp de Correo de Administración del sistema",
				AppConfigurationKey.ADMIN_EMAIL_SMTP_PORT, "587");
		appConfigurationDao.create(appConfigAdminEmailSmtpPort);
		
		AppConfiguration appConfigAdminEmailSmtpHost = new AppConfiguration("Host de Correo Administración",
				"Host de Correo de Administración del sistema",
				AppConfigurationKey.ADMIN_EMAIL_SMTP_HOST, "smtp.gmail.com");
		appConfigurationDao.create(appConfigAdminEmailSmtpHost);
		
		AppConfiguration appConfigAdminEmailSmtpTls = new AppConfiguration("Activacion tls de Correo Administración",
				"Activacion tls de Correo de Administración del sistema",
				AppConfigurationKey.ADMIN_EMAIL_SMTP_TLS, "true");
		appConfigurationDao.create(appConfigAdminEmailSmtpTls);
		
		AppConfiguration appConfigAdminEmailSmtpUser = new AppConfiguration("Usuario de Correo Administración",
				"Usuario de Correo de Administración del sistema",
				AppConfigurationKey.ADMIN_EMAIL_SMTP_USER, "sebassalazart@gmail.com");
		appConfigurationDao.create(appConfigAdminEmailSmtpUser);
		
		AppConfiguration appConfigAdminEmailSmtpAuth = new AppConfiguration("Activación autenticación smtp de Correo Administración",
				"Activación autenticación smtp de Correo de Administración del sistema",
				AppConfigurationKey.ADMIN_EMAIL_SMTP_AUTH, "true");
		appConfigurationDao.create(appConfigAdminEmailSmtpAuth);
		
		AppConfiguration appConfigPassTesetTime= new AppConfiguration("Tiempo de expiracion password reset",
				"Tiempo de expiracion para el correo de restablecimiento de contraseña en horas (solo números enteros)",
				AppConfigurationKey.USER_PASSWORD_RESET_TIME, "8");
		appConfigurationDao.create(appConfigPassTesetTime);
		
		AppConfiguration appConfigAppURL= new AppConfiguration("URL aplicación",
				"Url o dirección de la aplicación",
				AppConfigurationKey.APP_URL, "http://localhost:8080/web");
		appConfigurationDao.create(appConfigAppURL);
		
		LOGGER.info("createAppConfigs fin");


	}

	private void createUsers() {

		LOGGER.info("createUsers");
		

		Permiso permisoAdmin = permisoDao.findByNombreCodigo("ROLE_ADMIN");

		if (permisoAdmin == null) {
			permisoAdmin = new Permiso("Administrador", "ROLE_ADMIN", "Administradir des", Boolean.TRUE);

			permisoDao.create(permisoAdmin);
		}

		Rol rolAdmin = rolDao.findByNombre("Administrador");

		if (rolAdmin == null) {
			rolAdmin = new Rol();
			rolAdmin.setActivo(Boolean.TRUE);
			rolAdmin.setDescripcion("Test administrador");
			rolAdmin.setNombre("Administrador");
			rolAdmin.getPermisos().add(permisoAdmin);
			rolDao.create(rolAdmin);
		}

		Usuario admin = userDao.findByUsername("admin");

		if (admin == null) {
			admin = new Usuario();
			BCryptPasswordEncoder enc= new BCryptPasswordEncoder(11);
			
			admin.setPassword(enc.encode("admin"));
			admin.setUserName("admin");
			admin.setEmail("sebassalazart@gmail.com");
			admin.setActivo(Boolean.TRUE);
			admin.getRoles().add(rolAdmin);

			userDao.create(admin);
		}
		LOGGER.info("createUsers fin");
	}
}
