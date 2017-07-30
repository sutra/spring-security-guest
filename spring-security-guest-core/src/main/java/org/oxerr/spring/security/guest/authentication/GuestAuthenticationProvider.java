package org.oxerr.spring.security.guest.authentication;

import org.oxerr.spring.security.guest.core.userdetails.GuestUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class GuestAuthenticationProvider implements AuthenticationProvider {

	private final GuestUserDetailsService guestUserDetailsService;

	public GuestAuthenticationProvider(GuestUserDetailsService guestUserDetailsService) {
		this.guestUserDetailsService = guestUserDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		GuestAuthenticationToken guestAuthenticationToken = (GuestAuthenticationToken) authentication;
		return new GuestAuthenticationToken(guestUserDetailsService.loadUser(guestAuthenticationToken));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return GuestAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
