package com.sst.web.rest.users;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sst.generico10.model.users.Usuario;
import com.sst.services.config.AppDataConfig;
import com.sst.services.users.UserService;


@Path("/users")
@RequestScoped
public class UsersRestService {
	private static final Logger LOGGER = Logger.getLogger(AppDataConfig.class);
	
	@Inject
	UserService usersService;
	
	
	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> listAllUsers(){
		LOGGER.info("info listAllUsers");
		LOGGER.debug("info listAllUsers");
		return usersService.getAllUsers();
	}
	

}
