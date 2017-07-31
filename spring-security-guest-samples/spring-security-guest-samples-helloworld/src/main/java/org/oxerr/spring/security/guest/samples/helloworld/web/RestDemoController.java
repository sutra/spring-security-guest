package org.oxerr.spring.security.guest.samples.helloworld.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * curl -v -H 'X-Client-Token: 123' 'http://localhost:8080/'
 */
@RestController
@RolesAllowed("ROLE_GUEST")
public class RestDemoController {

	@RequestMapping(method = GET, path = "/", produces = "application/json")
	public Map<String, String> helloUser(
		Principal principal,
		Authentication authentication,
		HttpServletResponse response
	) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		result.put("name", principal.getName());
		return result;
	}

}
