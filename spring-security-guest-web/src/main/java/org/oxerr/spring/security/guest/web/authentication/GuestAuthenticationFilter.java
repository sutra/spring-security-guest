package org.oxerr.spring.security.guest.web.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.oxerr.spring.security.guest.authentication.GuestAuthenticationProvider;
import org.oxerr.spring.security.guest.authentication.GuestAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

public class GuestAuthenticationFilter extends OncePerRequestFilter {

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new GuestAuthenticationDetailsSource();
	private final GuestAuthenticationProvider guestAuthenticationProvider;

	public GuestAuthenticationFilter(GuestAuthenticationProvider guestAuthenticationProvider) {
		this.guestAuthenticationProvider = guestAuthenticationProvider;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getMethod().equals("OPTIONS") || super.shouldNotFilter(request);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (isGuest(request)) {
			SecurityContextHolder.getContext().setAuthentication(
				createAuthentication((HttpServletRequest) request));
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * Returns if a {@link GuestAuthenticationToken} should be created.
	 *
	 * @param request the {@link HttpServletRequest}.
	 * @return true to indicate that the method
	 * {@link #createAuthentication(HttpServletRequest)} should be invoked.
	 */
	protected boolean isGuest(HttpServletRequest request) {
		return SecurityContextHolder.getContext().getAuthentication() == null;
	}

	protected Authentication createAuthentication(HttpServletRequest request) {
		GuestAuthenticationToken auth = new GuestAuthenticationToken();
		auth.setDetails(authenticationDetailsSource.buildDetails(request));

		return guestAuthenticationProvider.authenticate(auth);
	}

	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
		Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

}
