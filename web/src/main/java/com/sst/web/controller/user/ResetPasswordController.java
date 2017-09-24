package com.sst.web.controller.user;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sst.generico10.utils.exceptions.CorreoException;
import com.sst.generico10.utils.exceptions.UsuarioException;
import com.sst.services.users.UserService;
import com.sst.web.controller.BaseController;

@Named("resetPasswordController")
@RequestScoped
public class ResetPasswordController extends BaseController {
	private static final long serialVersionUID = 1L;

	@Inject
	UserService userService;

	private String email;
	private String password;

	private String userId;
	private String token;

	public String resetPassword() {
		LOGGER.debug("" + this.password);
		LOGGER.debug("" + this.userId);
		LOGGER.debug("" + this.token);
		try {
			Long userIdLong = Long.parseLong(userId);

			userService.resetPassword(userIdLong, token, password);
		} catch (NumberFormatException e) {
			adicionarError("Error al recuperar el usuario, vuelva a intentarlo");
			return null;
		} catch (UsuarioException e) {
			adicionarError(e.getMessage());
			return null;
		}
		return "login.xhtml";
	}

	public String sendEmailResetPasword() {
		try {
			userService.sendEmailResetPasword(email);
			adicionarAvisoInfo("Se ha enviado un correo a su cuenta de correo con el que podrá restablecer su contraseña");
		} catch (UsuarioException | CorreoException e) {
			adicionarError(e.getMessage());
		}
		return null;

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}