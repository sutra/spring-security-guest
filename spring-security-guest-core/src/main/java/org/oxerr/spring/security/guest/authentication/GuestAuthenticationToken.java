package org.oxerr.spring.security.guest.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class GuestAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 2017073001L;

	private Object principal;

	public GuestAuthenticationToken() {
		super(null);
		setAuthenticated(false);
	}

	public GuestAuthenticationToken(UserDetails userDetails) {
		super(userDetails.getAuthorities());
		this.principal = userDetails;
		setAuthenticated(true);
	}

	/**
	 * Always returns an empty <code>String</code>
	 *
	 * @return an empty String
	 */
	@Override
	public Object getCredentials() {
		return "";
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

}
