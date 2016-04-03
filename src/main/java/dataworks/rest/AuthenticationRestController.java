package dataworks.rest;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthenticationRestController {

	@RequestMapping(value = "/authenticated", method = RequestMethod.GET)
	public String user(Principal user) {
		if (user != null) {
			return user.getName();
		}
		return null;
	}
	
	@RequestMapping(value = "/authorization", method = RequestMethod.GET)
	public Collection<?> authorization(Authentication authentication) {
		if (authentication != null) {
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return userDetails.getAuthorities();
		}
		return null;
	}
}
