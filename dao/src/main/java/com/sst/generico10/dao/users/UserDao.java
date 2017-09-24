package com.sst.generico10.dao.users;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.sst.generico10.dao.common.GenericDao;
import com.sst.generico10.model.users.Usuario;

@LocalBean
@Stateless
public class UserDao extends GenericDao<Usuario, Long> {

	public UserDao() {
		super(Usuario.class);

	}

	public Usuario findByUsername(String userName) {

		try {
			return (Usuario) entityManager.createNamedQuery("Usuario.findByUserName").setParameter("userName", userName)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public Usuario findByEmail(String email) {

		try {
			return (Usuario) entityManager.createNamedQuery("Usuario.findByEmail").setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

}
