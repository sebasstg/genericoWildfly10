package com.sst.generico10.model.appconfiguration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "app_config", schema = "appConfig")
@NamedQueries(
		{ 
			@NamedQuery(name="AppConfiguration.findByClave", query="SELECT o FROM AppConfiguration o WHERE o.clave=:clave") 	
		}
		)
public class AppConfiguration extends VersionedBaseEntity<Long> {

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
	@Size(min = 4, max = 100)
	private String nombre;

	@NotNull
	@NotEmpty
	@Column(name = "descripcion")
	@Size(min = 4, max = 255)
	private String descripcion;
	
	@NotNull
	@Column(name = "clave", unique=true)
	@Enumerated(EnumType.STRING)
	private AppConfigurationKey clave;
	
	@Size(min = 0, max = 255)
	@Column(name = "valor")
	private String valor ;
	

	public AppConfiguration(String nombre, String descripcion, AppConfigurationKey clave, String valor) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.clave = clave;
		this.valor = valor;
	}

	public AppConfiguration() {
		super();
	}

	@Override
	public Long getId() {
	
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
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

	public AppConfigurationKey getClave() {
		return clave;
	}

	public void setClave(AppConfigurationKey clave) {
		this.clave = clave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}


	
}
