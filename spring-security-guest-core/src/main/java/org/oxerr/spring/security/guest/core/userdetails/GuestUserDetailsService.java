package org.oxerr.spring.security.guest.core.userdetails;

import org.oxerr.spring.security.guest.authentication.GuestAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface GuestUserDetailsService {

	UserDetails loadUser(GuestAuthenticationToken guestAuthenticationToken);

}
