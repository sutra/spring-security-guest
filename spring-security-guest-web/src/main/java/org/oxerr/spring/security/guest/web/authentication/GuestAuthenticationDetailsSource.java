package org.oxerr.spring.security.guest.web.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;

public class GuestAuthenticationDetailsSource
		implements AuthenticationDetailsSource<HttpServletRequest, HttpServletRequest> {

	@Override
	public HttpServletRequest buildDetails(HttpServletRequest context) {
		return context;
	}

}
