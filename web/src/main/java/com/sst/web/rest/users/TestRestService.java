package com.sst.web.rest.users;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.sst.generico10.utils.exceptions.CorreoException;
import com.sst.generico10.utils.exceptions.UsuarioException;
import com.sst.services.config.AppDataConfig;
import com.sst.services.users.UserService;


@Path("/test")
@RequestScoped
public class TestRestService {
	private static final Logger LOGGER = Logger.getLogger(AppDataConfig.class);
	
	@Inject
	UserService userService;
	
	
	@Path("/echo/{text}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getEcho(@PathParam("text") String text){
		LOGGER.info("getEcho ");
		
		try {
			userService.sendEmailResetPasword("sebassalazart@gmail.com");
		} catch (CorreoException |UsuarioException e) {
			LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		LOGGER.info("fin ");
		return text; 
	}
	

}
