package portal.controllers;







import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.User;
import portal.service.RoleService;
import portal.service.SecurityService;
import portal.service.UserService;
import portal.service.Impl.UserValidator;
import portal.utility.Role;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
	@Autowired
	private RoleService roleService;
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
  

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private SecurityService securityService;
    
    @GetMapping("/users")
    public String supporttable(ModelMap model) {
    	model.addAttribute("users", userService.getAll());
    	model.addAttribute("userroles", roleService.getAll());
        return "users";
    }
    
    
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user,BindingResult bindingResult,ModelMap model){
    	
    	User pastUser=userService.findById(user.getId());

        userValidator.validate(getValidationByPassUser(pastUser,user), bindingResult);

        if (bindingResult.hasErrors()) {
        	model.addAttribute("user", user);
        	model.addAttribute("roles", roleService.getAll());
            return "userprofile";
        }

    	if(securityService.hasRole(Role.ADMIN) || securityService.hasRole(Role.SYSADMIN)) 
    	{
    		userService.updateUser(adminUpdatedUser(pastUser,user));
    		return "redirect:/users";
    	}
    	else
    	{
    		userService.updateUser(updatedUser(pastUser,user));
    		return "redirect:/userprofile";
    	}
   
        

    }   
    
    private User updatedUser(User pastUser,User formUser)
    {
    	pastUser.setUsername(formUser.getUsername());
    	pastUser.setStatus(formUser.getStatus());

    	if(!StringUtils.isEmpty(formUser.getPasswordchg()))
    	{
    		pastUser.setPassword(bCryptPasswordEncoder.encode(formUser.getPasswordchg()));
    	}
    	pastUser.setPasswordchg(null);
    	pastUser.setPasswordconfirm(null);
    	
    	return pastUser;
    }
    
    private User adminUpdatedUser(User pastUser,User formUser)
    {
    	pastUser.setUsername(formUser.getUsername());
    	pastUser.setStatus(formUser.getStatus());
    	pastUser.setRoles(formUser.getRoles());
 

    	
    	if(!StringUtils.isEmpty(formUser.getPasswordchg()))
    	{
    		pastUser.setPassword(bCryptPasswordEncoder.encode(formUser.getPasswordchg()));
    	}
    	pastUser.setPasswordchg(null);
    	pastUser.setPasswordconfirm(null);
    	
    	return pastUser;
    }    
    
    private User getValidationByPassUser(User pastUser,User formUser)
    {
    	//set all by pass rule
    	if(!StringUtils.isEmpty(formUser.getUsername()) && pastUser.getUsername().equals(formUser.getUsername()))
    	{
    		formUser.setNamebypass(true);
    	}
    	
    	return formUser;
    }
    

	@GetMapping("/userprofile")
    public String userprofile(ModelMap model,@RequestParam(name="id", required=false) String id) {
      	if(!StringUtils.isEmpty(id) && (securityService.hasRole(Role.ADMIN) || securityService.hasRole(Role.SYSADMIN))) 
      	{
      		model.addAttribute("user", userService.findById(Long.valueOf(id)));
      	}
      	else
      	{
      		model.addAttribute("user", securityService.getCurrentUserProfile());
      	}
        model.addAttribute("roles", roleService.getAll());
        return "userprofile";
    }
    
    @GetMapping("/addUser")
    public String registration(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAll());
        return "adduser";
    }  
    
    @PostMapping("/addUser")
    public String registration(@ModelAttribute("user") User userForm, BindingResult bindingResult, ModelMap model) {
    	userForm.setNamebypass(false);
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
        	model.addAttribute("user", userForm);
        	model.addAttribute("roles", roleService.getAll());
            return "adduser";
        }
        userForm.setPasswordconfirm(null);
        userService.saveUser(userForm);

        //securityService.autologin(userForm.getUsername(), userForm.getPasswordconfirm());

        return "redirect:/users";
    } 
    
    @GetMapping("/deleteUser")
    public String DeleteAppInstance(@RequestParam(name="id", required=true) String id) {
    	User user = new User();
    	user.setId(Long.valueOf(id));
    	userService.updateUser(user);
    	userService.deleteUser(user);
    	return "redirect:/users";
    }    
    
    @GetMapping("/login")
    public String login(ModelMap model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Invalid Username/Password.");

        if (logout != null)
            model.addAttribute("message", "Log Out Successfully.");

        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	    if (auth != null){    
    	        new SecurityContextLogoutHandler().logout(request, response, auth);
    	    }
    	    return "redirect:/login?logout";
    }
	
}