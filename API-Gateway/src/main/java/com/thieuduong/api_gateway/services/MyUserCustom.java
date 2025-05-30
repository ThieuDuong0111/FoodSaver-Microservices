package com.thieuduong.api_gateway.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

public class MyUserCustom implements MyUserDetails, CredentialsContainer {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private static final Log logger = LogFactory.getLog(User.class);

	private String password;

	private Integer id;

	private final String username;

	private final Set<GrantedAuthority> authorities;

	private final boolean accountNonExpired;

	private final boolean accountNonLocked;

	private final boolean credentialsNonExpired;

	private final boolean enabled;

	public MyUserCustom(Integer id, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this(id, username, password, true, true, true, true, authorities);
	}

	public MyUserCustom(Integer id, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		Assert.isTrue(username != null && !"".equals(username) && password != null,
				"Cannot pass null or empty values to constructor");
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public void eraseCredentials() {
		this.password = null;
	}

	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		// Ensure array iteration order is predictable (as per
		// UserDetails.getAuthorities() contract and SEC-717)
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());
		for (GrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}
		return sortedAuthorities;
	}

	/**
	 * Returns {@code true} if the supplied object is a {@code User} instance with
	 * the same {@code username} value.
	 * <p>
	 * In other words, the objects are equal if they have the same username,
	 * representing the same principal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User user) {
			return this.username.equals(user.getUsername());
		}
		return false;
	}

	/**
	 * Returns the hashcode of the {@code username}.
	 */
	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName()).append(" [");
		sb.append("Username=").append(this.username).append(", ");
		sb.append("Password=[PROTECTED], ");
		sb.append("Enabled=").append(this.enabled).append(", ");
		sb.append("AccountNonExpired=").append(this.accountNonExpired).append(", ");
		sb.append("CredentialsNonExpired=").append(this.credentialsNonExpired).append(", ");
		sb.append("AccountNonLocked=").append(this.accountNonLocked).append(", ");
		sb.append("Granted Authorities=").append(this.authorities).append("]");
		return sb.toString();
	}

	public static UserBuilder withUsername(String username) {
		return builder().username(username);
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}

	@Deprecated
	public static UserBuilder withDefaultPasswordEncoder() {
		logger.warn("User.withDefaultPasswordEncoder() is considered unsafe for production "
				+ "and is only intended for sample applications.");
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return builder().passwordEncoder(encoder::encode);
	}

	public static UserBuilder withUserDetails(UserDetails userDetails) {
		// @formatter:off
		return withUsername(userDetails.getUsername())
				.password(userDetails.getPassword())
				.accountExpired(!userDetails.isAccountNonExpired())
				.accountLocked(!userDetails.isAccountNonLocked())
				.authorities(userDetails.getAuthorities())
				.credentialsExpired(!userDetails.isCredentialsNonExpired())
				.disabled(!userDetails.isEnabled());
		// @formatter:on
	}

	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		@Override
		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			// Neither should ever be null as each entry is checked before adding it to
			// the set. If the authority is null, it is a custom authority and should
			// precede others.
			if (g2.getAuthority() == null) {
				return -1;
			}
			if (g1.getAuthority() == null) {
				return 1;
			}
			return g1.getAuthority().compareTo(g2.getAuthority());
		}

	}

	public static final class UserBuilder {
		private Integer id;

		private String username;

		private String password;

		private List<GrantedAuthority> authorities = new ArrayList<>();

		private boolean accountExpired;

		private boolean accountLocked;

		private boolean credentialsExpired;

		private boolean disabled;

		private Function<String, String> passwordEncoder = (password) -> password;

		private UserBuilder() {
		}

		public UserBuilder id(Integer id) {
			Assert.notNull(id, "id cannot be null");
			this.id = id;
			return this;
		}

		public UserBuilder username(String username) {
			Assert.notNull(username, "username cannot be null");
			this.username = username;
			return this;
		}

		public UserBuilder password(String password) {
			Assert.notNull(password, "password cannot be null");
			this.password = password;
			return this;
		}

		public UserBuilder passwordEncoder(Function<String, String> encoder) {
			Assert.notNull(encoder, "encoder cannot be null");
			this.passwordEncoder = encoder;
			return this;
		}

		public UserBuilder roles(String... roles) {
			List<GrantedAuthority> authorities = new ArrayList<>(roles.length);
			for (String role : roles) {
				Assert.isTrue(!role.startsWith("ROLE_"),
						() -> role + " cannot start with ROLE_ (it is automatically added)");
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			}
			return authorities(authorities);
		}

		public UserBuilder authorities(GrantedAuthority... authorities) {
			Assert.notNull(authorities, "authorities cannot be null");
			return authorities(Arrays.asList(authorities));
		}

		public UserBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
			Assert.notNull(authorities, "authorities cannot be null");
			this.authorities = new ArrayList<>(authorities);
			return this;
		}

		public UserBuilder authorities(String... authorities) {
			Assert.notNull(authorities, "authorities cannot be null");
			return authorities(AuthorityUtils.createAuthorityList(authorities));
		}

		public UserBuilder accountExpired(boolean accountExpired) {
			this.accountExpired = accountExpired;
			return this;
		}

		public UserBuilder accountLocked(boolean accountLocked) {
			this.accountLocked = accountLocked;
			return this;
		}

		public UserBuilder credentialsExpired(boolean credentialsExpired) {
			this.credentialsExpired = credentialsExpired;
			return this;
		}

		public UserBuilder disabled(boolean disabled) {
			this.disabled = disabled;
			return this;
		}

		public MyUserDetails build() {
			String encodedPassword = this.passwordEncoder.apply(this.password);
			return new MyUserCustom(this.id, this.username, encodedPassword, !this.disabled, !this.accountExpired,
					!this.credentialsExpired, !this.accountLocked, this.authorities);
		}

	}

}
