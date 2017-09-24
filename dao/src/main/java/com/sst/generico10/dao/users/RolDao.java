package com.sst.generico10.dao.users;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.sst.generico10.dao.common.GenericDao;
import com.sst.generico10.model.users.Rol;

@LocalBean
@Stateless
public class RolDao extends GenericDao<Rol, Long>{

	public RolDao() {
		super(Rol.class);
		
	}
	
	public Rol findByNombre(String nombre){
		
		try {
			return (Rol) entityManager.createNamedQuery("Rol.findByNombre").setParameter("nombre",nombre).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	
	
	

}
