package com.thieuduong.api_gateway.services;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface MyUserDetails extends Serializable {

	Collection<? extends GrantedAuthority> getAuthorities();

	Integer getId();

	String getPassword();

	String getUsername();

	default boolean isAccountNonExpired() {
		return true;
	}

	default boolean isAccountNonLocked() {
		return true;
	}

	default boolean isCredentialsNonExpired() {
		return true;
	}

	default boolean isEnabled() {
		return true;
	}

}
