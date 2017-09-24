package com.sst.generico10.model.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.sst.generico10.model.common.VersionedBaseEntity;
@Entity
@Table(name="permisos", schema="usuarios")
@NamedQueries(
		{ 
			@NamedQuery(name="Permiso.findByNombreCodigo", query="SELECT o FROM Permiso o WHERE o.nombreCodigo=:nombreCodigo") 	
		}
		)
public class Permiso extends VersionedBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	

	public Permiso() {
		super();
	}
	
	

	public Permiso(String nombre, String nombreCodigo, String descripcion, Boolean activo) {
		super();
		this.nombre = nombre;
		this.nombreCodigo = nombreCodigo;
		this.descripcion = descripcion;
		this.activo = activo;
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@NotEmpty
	@Column(name = "nombre", unique = true)
	@Size(min = 4, max = 25)
	private String nombre;

	@NotNull
	@NotEmpty
	@Column(name = "nombreCodigo", unique = true)
	@Size(min = 4, max = 15)
	private String nombreCodigo;

	@NotNull
	@NotEmpty
	@Column(name = "descripcion")
	@Size(min = 4, max = 255)
	private String descripcion;

	@NotNull
	@Column(name = "activo")
	private Boolean activo;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean isActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getNombreCodigo() {
		return nombreCodigo;
	}

	public void setNombreCodigo(String nombreCodigo) {
		this.nombreCodigo = nombreCodigo;
	}

	public Boolean getActivo() {
		return activo;
	}

	
}
