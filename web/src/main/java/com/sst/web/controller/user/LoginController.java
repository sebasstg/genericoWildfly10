package com.sst.web.controller.user;


import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;


import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(LoginController.class);

	private static final long serialVersionUID = 1L;
	private String username;
    private String password;

    public String login() throws ServletException, IOException {
    	LOGGER.debug("username: "+this.username);
        String url = "/login?username=" + username + "&password=" + password;
        LOGGER.debug("url "+url);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext extenalContext = facesContext.getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest)extenalContext.getRequest()).getRequestDispatcher(url);
        dispatcher.forward((ServletRequest)extenalContext.getRequest(), (ServletResponse)extenalContext.getResponse());
        facesContext.responseComplete();
        return null;
    }
    
    public String logout() throws ServletException, IOException {
    	LOGGER.debug("username: "+this.username);
        String url = "/logout";
        LOGGER.debug("url "+url);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext extenalContext = facesContext.getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest)extenalContext.getRequest()).getRequestDispatcher(url);
        dispatcher.forward((ServletRequest)extenalContext.getRequest(), (ServletResponse)extenalContext.getResponse());
        facesContext.responseComplete();
        return null;
    }
   
    @PostConstruct
    public void init(){
    	LOGGER.debug("---------+++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	LOGGER.debug("---------+++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	LOGGER.debug("---------"    			+ ""+p);
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	LOGGER.debug("---------"    			+ ""+auth);
    	 ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
    	 LOGGER.info(context.getUserPrincipal());
    	LOGGER.info(this);
    	LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
     
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
       
    }

    
}