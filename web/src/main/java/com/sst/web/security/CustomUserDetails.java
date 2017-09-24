package com.sst.web.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sst.generico10.model.users.Permiso;
import com.sst.generico10.model.users.Rol;
import com.sst.generico10.model.users.Usuario;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	Usuario usuario;

	public CustomUserDetails() {
		super();
	}

	public CustomUserDetails(Usuario usuario) {
		super();
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();

		if (CollectionUtils.isEmpty(usuario.getRoles())) {
			return authorities;
		}

		for (Rol rol : usuario.getRoles()) {
			if (!rol.getActivo())
				continue;
			if (!CollectionUtils.isEmpty(rol.getPermisos())) {

				for (Permiso permiso : rol.getPermisos()) {
					if (!permiso.getActivo())
						continue;
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permiso.getNombreCodigo());
					authorities.add(grantedAuthority);
				}
			}
		}
		return authorities;
	}

	@Override
	public String getPassword() {

		return usuario.getPassword();
	}

	@Override
	public String getUsername() {

		return usuario.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
