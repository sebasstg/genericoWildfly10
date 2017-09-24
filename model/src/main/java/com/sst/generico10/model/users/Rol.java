package com.sst.generico10.model.users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.sst.generico10.model.common.VersionedBaseEntity;

@Entity
@Table(name = "roles", schema = "usuarios")
@NamedQueries(
		{ 
			@NamedQuery(name="Rol.findByNombre", query="SELECT o FROM Rol o WHERE o.nombre=:nombre") 	
		}
		)
public class Rol extends VersionedBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	@Column(name = "descripcion")
	@Size(min = 4, max = 255)
	private String descripcion;

	@NotNull
	@Column(name = "activo")
	private Boolean activo;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name = "roles_permisos", schema="usuarios",
		joinColumns = 
		@JoinColumn(name = "role_id", 
		referencedColumnName = "id",
		foreignKey=@ForeignKey(name="fk_roles_roles_permisos")),
		inverseJoinColumns = @JoinColumn(name = "permiso_id", 
		referencedColumnName = "id",
		foreignKey=@ForeignKey(name="fk_permisos_roles_permisos")),
		uniqueConstraints=@UniqueConstraint(columnNames={"role_id","permiso_id"})
			)
	private List<Permiso> permisos;

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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<Permiso> getPermisos() {
		if(permisos==null){
			permisos= new ArrayList<>();
		}
		return permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}

	
}
