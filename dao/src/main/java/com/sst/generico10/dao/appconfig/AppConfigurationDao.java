package com.sst.generico10.dao.appconfig;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.sst.generico10.dao.common.GenericDao;
import com.sst.generico10.model.appconfiguration.AppConfiguration;
import com.sst.generico10.model.appconfiguration.AppConfigurationKey;

@LocalBean
@Stateless
public class AppConfigurationDao extends GenericDao<AppConfiguration, Long>{

	public AppConfigurationDao() {
		super(AppConfiguration.class);
		
	}
	
	public AppConfiguration findByClave(AppConfigurationKey clave){
		
		try {
			return (AppConfiguration) entityManager.createNamedQuery("AppConfiguration.findByClave").setParameter("clave",clave).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	
	
	

}
