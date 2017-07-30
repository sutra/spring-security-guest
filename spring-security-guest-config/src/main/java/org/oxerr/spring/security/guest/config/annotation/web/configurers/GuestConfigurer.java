package org.oxerr.spring.security.guest.config.annotation.web.configurers;

import org.oxerr.spring.security.guest.authentication.GuestAuthenticationProvider;
import org.oxerr.spring.security.guest.core.userdetails.GuestUserDetailsService;
import org.oxerr.spring.security.guest.web.authentication.GuestAuthenticationFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

public final class GuestConfigurer<H extends HttpSecurityBuilder<H>>
		extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, H> {

	private GuestAuthenticationFilter authenticationFilter;

	private final GuestUserDetailsService guestUserDetailsService;

	public GuestConfigurer(GuestUserDetailsService guestUserDetailsService) {
		this.guestUserDetailsService = guestUserDetailsService;
	}

	/**
	 * Sets the {@link GuestAuthenticationFilter} used to populate a guest user.
	 * If this is set, no attributes on the {@link GuestConfigurer} will be set on the
	 * {@link GuestAuthenticationFilter}.
	 *
	 * @param authenticationFilter the {@link GuestAuthenticationFilter} used to
	 * populate an guest user.
	 *
	 * @return the {@link GuestConfigurer} for further customization of guest
	 * authentication
	 */
	public GuestConfigurer<H> authenticationFilter(GuestAuthenticationFilter authenticationFilter) {
		this.authenticationFilter = authenticationFilter;
		return this;
	}

	@Override
	public void init(H http) throws Exception {
		super.init(http);

		GuestAuthenticationProvider guestAuthenticationProvider = new GuestAuthenticationProvider(
				guestUserDetailsService);
		postProcess(guestAuthenticationProvider);
		http.authenticationProvider(guestAuthenticationProvider);

		if (authenticationFilter == null) {
			authenticationFilter = new GuestAuthenticationFilter(guestAuthenticationProvider);
		}
	}

	@Override
	public void configure(H http) throws Exception {
		authenticationFilter.afterPropertiesSet();
		http.addFilterBefore(authenticationFilter, AnonymousAuthenticationFilter.class);

		super.configure(http);
	}

}
