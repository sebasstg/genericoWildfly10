package com.sst.generico10.dao.users;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.sst.generico10.dao.common.GenericDao;
import com.sst.generico10.model.users.Permiso;

@LocalBean
@Stateless
public class PermisoDao extends GenericDao<Permiso, Long>{

	public PermisoDao() {
		super(Permiso.class);
		
	}
	
	public Permiso findByNombreCodigo(String nombreCodigo){
		
		try {
			return (Permiso) entityManager.createNamedQuery("Permiso.findByNombreCodigo").setParameter("nombreCodigo",nombreCodigo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	
	
	

}
