package portal.rest.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import portal.models.ApiLogin;
import portal.models.JSESSIONID;

import portal.service.SecurityService;

@RestController
@RequestMapping("/api")
public class AuthRestController {
    @Autowired
    private SecurityService securityService;
	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<JSESSIONID> apilogin(@RequestBody ApiLogin login)
	{
		String sessionid=securityService.apilogin(login.getUsername(), login.getPassword());
		if(!StringUtils.isEmpty(sessionid))
		{

			return new ResponseEntity<JSESSIONID>(new JSESSIONID(sessionid),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<JSESSIONID>(HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<String> apilogout(HttpServletRequest request, HttpServletResponse response)
	{
   	 	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	        return new ResponseEntity<String>("Logout Successfully",HttpStatus.ACCEPTED);
	    }
	    else
	    {
	    	return new ResponseEntity<String>("Logout Fail",HttpStatus.BAD_REQUEST);
	    }
		
	}
	

}
