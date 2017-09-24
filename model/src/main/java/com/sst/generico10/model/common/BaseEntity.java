package com.sst.generico10.model.common;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<PK> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public abstract PK getId();
	public abstract void setId(PK id);
}
