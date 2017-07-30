package org.oxerr.spring.security.guest.samples.helloworld;

import org.oxerr.spring.security.guest.config.annotation.web.configurers.GuestConfigurer;
import org.oxerr.spring.security.guest.core.userdetails.GuestUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private GuestUserDetailsService guestUserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.anyRequest()
				.permitAll()
				.and()
			.apply(new GuestConfigurer<>(guestUserDetailsService));
	}

}
