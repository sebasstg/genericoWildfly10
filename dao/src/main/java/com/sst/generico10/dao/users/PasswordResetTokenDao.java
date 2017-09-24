package com.sst.generico10.dao.users;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.sst.generico10.dao.common.GenericDao;
import com.sst.generico10.model.users.PasswordResetToken;

@LocalBean
@Stateless
public class PasswordResetTokenDao extends GenericDao<PasswordResetToken, Long> {

	public PasswordResetTokenDao() {
		super(PasswordResetToken.class);

	}
	
	public PasswordResetToken findByUserId(Long userId) {
		
		try {
			return (PasswordResetToken) entityManager.createNamedQuery("PasswordResetToken.findByUserId").setParameter("userId", userId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}



}
