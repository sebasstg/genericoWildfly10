package com.sst.services.users;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sst.generico10.dao.users.PasswordResetTokenDao;
import com.sst.generico10.dao.users.UserDao;
import com.sst.generico10.model.users.PasswordResetToken;
import com.sst.generico10.model.users.Usuario;
import com.sst.generico10.utils.FileUtils;
import com.sst.generico10.utils.exceptions.CorreoException;
import com.sst.generico10.utils.exceptions.GeneralAppException;
import com.sst.generico10.utils.exceptions.UsuarioException;
import com.sst.services.appconfiguration.annotations.AdminEmail;
import com.sst.services.appconfiguration.annotations.AppUrl;
import com.sst.services.appconfiguration.annotations.PasswordResetEmailTime;
import com.sst.services.correo.CorreoService;

@Stateless
public class UserService {
	
	private static final Logger LOGGER= Logger.getLogger(UserService.class);
	
	@Inject
	private UserDao usersDao;
	
	@Inject
	private PasswordResetTokenDao passwordResetTokenDao;
	
	@Inject
	private CorreoService correoService;
	
	@Inject
	FileUtils fileUtils;
	
	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@PasswordResetEmailTime
	private int passwordResetEmailTime;
	
	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@AdminEmail
	private String adminEmail;
	
	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@AppUrl
	private String appUrl;
	
	
	private static final BCryptPasswordEncoder enc= new BCryptPasswordEncoder(11);
	
	
	public Usuario createNewUser(Usuario usuario){
		
		usuario.setPassword(enc.encode(usuario.getPassword()));
		
		return usersDao.create(usuario);
	}
	
	/**
	 * PErmite restablecer contraseña
	 * @param userId id de usuario
	 * @param token token para validación de de correo
	 * @param password nueva contraseña
	 * @throws UsuarioException 
	 */
	public void resetPassword(Long userId, String token, String password) throws UsuarioException {
		
		PasswordResetToken passToken = passwordResetTokenDao.findByUserId(userId);
		
		
		if(passToken==null) {
			LOGGER.warn("no se encontro token para el usuario con id "+userId);
			throw new UsuarioException("No se pudo encontrar el permiso para restablecer la contraseña, vuelva a intentarlo.");
		}
		
		Usuario usuario = usersDao.find(userId);
		if(usuario==null) {
			LOGGER.warn("no se encontro el usuario con id "+userId);
			throw new UsuarioException("No se pudo encontrar el usuario para restablecer la contraseña, vuelva a intentarlo.");
		}
		
		if(!passToken.getToken().equals(passToken.getToken())) {
			LOGGER.warn("el token es inválido, token proporcionado "+token+" token recuperado "+passToken.getToken());
			throw new UsuarioException("No se pudo encontrar el usuario para restablecer la contraseña, vuelva a intentarlo.");
		}
		
		if(passToken.getExpiryDate().before((new GregorianCalendar()).getTime())) {
			LOGGER.warn("el token ha expirado");
			throw new UsuarioException("No se pudo restablecer la contraseña, la validación de seguridad ha expirado, vuelva a intentarlo.");
		}
		
		// restableco contraseña
		
		usuario.setPassword(enc.encode(password));
		
		usersDao.update(usuario);
		
		// borro token
		
		passwordResetTokenDao.delete(passToken);
		
	}
	public void sendEmailResetPasword(String email) throws UsuarioException, CorreoException{
		
		Usuario usuario = usersDao.findByEmail(email);
		if(usuario==null) {
			throw new UsuarioException("Usuario con correo "+email+" no encontrado.");
		}
		
		// creo el toquen para este usuario
		
		String token=UUID.randomUUID().toString();
		
		PasswordResetToken passwordResetToken=passwordResetTokenDao.findByUserId(usuario.getId());
		
		if(passwordResetToken==null) passwordResetToken= new PasswordResetToken();
		
		GregorianCalendar expirationDate = new GregorianCalendar();
		expirationDate.add(GregorianCalendar.HOUR, passwordResetEmailTime);
		
		passwordResetToken.setExpiryDate(expirationDate.getTime());
		passwordResetToken.setToken(token);
		passwordResetToken.setUsuario(usuario);
		
		passwordResetTokenDao.update(passwordResetToken);
		
		sendResetPasswordEmail(usuario, passwordResetToken);	
	}
	
	
	
	
	
	private void sendResetPasswordEmail(Usuario usuario, PasswordResetToken passwordResetToken) throws CorreoException {
		
		try {
			String text=fileUtils.readFileAsStringFromResourceFolder("templates/RestablecimientoContrasenaCorreo.html");
			 String url = appUrl + "/pages/public/resetPassword.xhtml?id=" + 
					 usuario.getId() + "&token=" + passwordResetToken.getToken();
			text=StringUtils.replace(text, "##name##", usuario.getUserName());
			text=StringUtils.replace(text, "##action_url##",url );
			text=StringUtils.replace(text, "##support_email##",adminEmail);
			
			
			correoService.enviarCorreoElectronicoDeAdmin("Restablecimiento de contraseña ", text, usuario.getEmail(), true);
			
		} catch (GeneralAppException e) {
			throw new CorreoException("No se pudo enviar el correo de restablecimiento de contraseña ("+e.getMessage()+")");
		}
		
	}

	public Usuario getUserById(Long id) {
		return usersDao.find(id);
	}
	
	public Usuario getUserByUsername(String userName) {
		return usersDao.findByUsername(userName);
	}

	public List<Usuario> getAllUsers(){
		return usersDao.findAll();
	}

}
