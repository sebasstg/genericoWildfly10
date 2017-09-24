package com.sst.web.security;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sst.generico10.model.users.Usuario;
import com.sst.services.users.UserService;

@EJB(name = "userService", beanInterface = UserService.class)
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger LOGGER = Logger.getLogger(CustomUserDetailsService.class);

	@Inject
	UserService userService;

	private static final String USER_SERVICE_EJB_LOOKUP_PATH = "java:module/UserService";

	@Override
	public UserDetails loadUserByUsername(String username) {

		Usuario usuario = getUser(username);
		if (usuario == null) {
			String mensajeError = "Nombre de usuario no encontrado";
			LOGGER.warn(mensajeError);
			throw new UsernameNotFoundException(mensajeError);
		} else {
			return new CustomUserDetails(usuario);

		}

	}

	private void setUserService() {
		try {
			InitialContext initialContext = new InitialContext();
			userService = (UserService) initialContext.lookup(USER_SERVICE_EJB_LOOKUP_PATH);
		} catch (NamingException ex) {

			LOGGER.error("Could not lookup for EJB userService with lookup path " + USER_SERVICE_EJB_LOOKUP_PATH);
			LOGGER.error(ExceptionUtils.getStackTrace(ex));
		}
	}

	private Usuario getUser(String userName) {

		if (userService == null)
			setUserService();
		return userService.getUserByUsername(userName);

	}

}
