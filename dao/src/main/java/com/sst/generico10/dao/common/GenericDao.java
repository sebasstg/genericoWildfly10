package com.sst.generico10.dao.common;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericDao<T, PK> {

	@PersistenceContext(unitName = "primary") 
	protected  EntityManager entityManager;

	protected Class<T> entityClass;
	
	public Class<T> getEntityClass(){
		return entityClass;
				
	}

	public GenericDao(Class<T> entityClass) {
		this.entityClass=entityClass;
		
	}
	
	

	
	public T create(T t) {
		this.entityManager.persist(t);
		return t;
	}

	
	public T find(PK id) {
		return this.entityManager.find(entityClass, id);
	}
	
	
    public List<T> findAll() {
        CriteriaQuery<T> criteria = entityManager.getCriteriaBuilder()
                .createQuery(getEntityClass());
        criteria = criteria.select(criteria.from(getEntityClass()));
        return (List<T>) entityManager.createQuery(criteria).getResultList();
    }


	
	public T update(T t) {
		return this.entityManager.merge(t);
	}

	
	public void delete(T t) {
		t = this.entityManager.merge(t);
		this.entityManager.remove(t);
	}
	
		
	

}
