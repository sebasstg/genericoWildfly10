package com.sst.generico10.model.common;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class VersionedBaseEntity<PK> extends BaseEntity<PK>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Version
	@Column(name="version", nullable=false)
	public int version;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	
	
}
