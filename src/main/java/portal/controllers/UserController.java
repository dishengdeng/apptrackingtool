package portal.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import portal.service.UserService;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
	@Autowired
	private RoleService roleService;
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //@Autowired
    //private SecurityService securityService;

//    @Autowired
//    private UserValidator userValidator;
    
    @GetMapping("/users")
    public String supporttable(ModelMap model) {
    	model.addAttribute("users", userService.getAll());
    	model.addAttribute("userroles", roleService.getAll());
    	model.addAttribute("userModel", new User());
        return "users";
    }
    
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("userModel") User user) {
    	
    	if(user.getPasswordchg()!=null && !user.getPasswordchg().isEmpty())
    	{
    		user.setPassword(bCryptPasswordEncoder.encode(user.getPasswordchg()));
    		user.setPasswordchg(null);
    	}

    	userService.updateUser(user);
        return "redirect:/users";
    }    
    
    @GetMapping("/addUser")
    public String registration(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAll());
        return "adduser";
    }  
    
    @PostMapping("/addUser")
    public String registration(@ModelAttribute("user") User userForm, BindingResult bindingResult, ModelMap model) {
//        userValidator.validate(userForm, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//        	model.addAttribute("user", userForm);
//            return "adduser";
//        }

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
