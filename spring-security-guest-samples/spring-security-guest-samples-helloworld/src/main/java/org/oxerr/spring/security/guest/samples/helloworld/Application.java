package org.oxerr.spring.security.guest.samples.helloworld;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.spring.security.guest.authentication.GuestAuthenticationToken;
import org.oxerr.spring.security.guest.core.userdetails.GuestUserDetailsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class Application {

	private final Logger log = LogManager.getLogger();

	/**
	 * Writes the request URI (and optionally the query string) to the Commons Log.
	 *
	 * @return the filter registration bean for the {@link CommonsRequestLoggingFilter}.
	 */
	@Bean
	public FilterRegistrationBean commonsRequestLoggingFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		CommonsRequestLoggingFilter requestLoggingFilter = new CommonsRequestLoggingFilter();
		registrationBean.setFilter(requestLoggingFilter);
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}

	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}

	@Bean
	public GuestUserDetailsService guestUserDetailsService() {
		return new GuestUserDetailsService() {

			@Override
			public UserDetails loadUser(GuestAuthenticationToken guestAuthenticationToken) {
				String clientToken = (String) guestAuthenticationToken.getPrincipal();
				log.debug("Loading user for {}", clientToken);
				return new User("guest " + clientToken, "", AuthorityUtils.createAuthorityList("ROLE_GUEST"));
			}

		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
