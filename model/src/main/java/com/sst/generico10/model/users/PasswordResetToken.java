package com.sst.generico10.model.users;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sst.generico10.model.common.VersionedBaseEntity;

@Entity
@Table(name="password_reset_token", schema="usuarios")
@NamedQueries(
		{ 
			@NamedQuery(name="PasswordResetToken.findByUserId", query="SELECT o FROM PasswordResetToken o WHERE o.usuario.id=:userId")
			
		}
		
		)
public class PasswordResetToken  extends VersionedBaseEntity<Long>{
  
	private static final long serialVersionUID = 1L;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
  
    @Column(name="token")
    private String token;
  
    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userio_id")
    private Usuario usuario;
  
    @Column(name="expiryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
}