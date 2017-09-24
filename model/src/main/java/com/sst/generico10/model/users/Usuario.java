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

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.sst.generico10.model.common.VersionedBaseEntity;

@Entity
@Table(name="usuarios", schema="usuarios")
@NamedQueries(
		{ 
			@NamedQuery(name="Usuario.findByUserName", query="SELECT o FROM Usuario o WHERE o.userName=:userName"),
			@NamedQuery(name="Usuario.findByEmail", query="SELECT o FROM Usuario o WHERE o.email=:email")
			
		}
		
		)
public class Usuario extends VersionedBaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public Usuario() {
		super();
	}
	
	

	public Usuario(String userName, String password, String email, Boolean activo, List<Rol> roles) {
		super();
		this.userName = userName; 
		this.password = password;
		this.email = email;
		this.activo = activo;
		this.roles = roles;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@NotNull(message="Es necesario definir un nombre de usuario para el usuario.")
    @NotEmpty
    @Size(min = 4, max = 25)
	@Column(name="userName", unique=true)
	private String userName;
	
	
	@Column(name="password")
	@NotNull(message="Es necesario definir una contraseña para el usuario.")
    @NotEmpty
	//@Size(min = 4, max = 25)
	private String password;
	
	@NotNull(message="Es necesario definir un correo electrónico para el usuario.")
    @NotEmpty
    @Email
    @Column(name="correo", unique=true)
    private String email;
	
	@NotNull(message="Es necesario definir un estado para el usuario.")
	@Column(name = "activo")
	private Boolean activo;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="usuarios_roles", schema="usuarios",
		joinColumns=@JoinColumn(name="usuario_id",referencedColumnName="id",
				foreignKey=@ForeignKey(name="fk_usuarios_roles")
				),
		inverseJoinColumns=@JoinColumn(name="rol_id",referencedColumnName="id",
		foreignKey=@ForeignKey(name="fk_roles_usuarios")
				),
		uniqueConstraints=@UniqueConstraint(columnNames={"usuario_id","rol_id"})
		
			)
	private List<Rol> roles;
	
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public Boolean isActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<Rol> getRoles() {
		if(roles==null){
			roles= new ArrayList<>();
		}
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email + "]";
	}
	
	
	
	

}
