package org.oxerr.spring.security.guest.config.annotation.web.configurers;

import org.oxerr.spring.security.guest.authentication.GuestAuthenticationProvider;
import org.oxerr.spring.security.guest.core.userdetails.GuestUserDetailsService;
import org.oxerr.spring.security.guest.web.authentication.GuestAuthenticationFilter;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public final class GuestConfigurer<H extends HttpSecurityBuilder<H>>
		extends AbstractAuthenticationFilterConfigurer<H, GuestConfigurer<H>, GuestAuthenticationFilter> {

	private final GuestUserDetailsService guestUserDetailsService;

	public GuestConfigurer(GuestUserDetailsService guestUserDetailsService) {
		super(new GuestAuthenticationFilter(), "/");
		this.guestUserDetailsService = guestUserDetailsService;
	}

	@Override
	protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
		return new AntPathRequestMatcher("/");
	}

	@Override
	public void init(H http) throws Exception {
		super.init(http);

		GuestAuthenticationProvider guestAuthenticationProvider = new GuestAuthenticationProvider(
				guestUserDetailsService);
		postProcess(guestAuthenticationProvider);
		http.authenticationProvider(guestAuthenticationProvider);
	}

	@Override
	public void configure(H http) throws Exception {

		// Make sure the filter be registered in
		// org.springframework.security.config.annotation.web.builders.FilterComparator
		http.addFilterBefore(getAuthenticationFilter(), AnonymousAuthenticationFilter.class);

		super.configure(http);
	}

}
