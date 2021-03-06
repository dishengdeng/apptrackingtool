package portal.controllers;



import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;



import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController{


	    private static final String PATH = "/error";




	    @RequestMapping(value = PATH, produces={"text/html"})
	    public String error(ModelMap model, HttpServletRequest request) {
	    	String errorMessage;
	    	Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    	Object error = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
	    	
	    	if(!StringUtils.isEmpty(status) && status.toString().equals("403"))
	    	{
	    		errorMessage="Access Denied.";
	    	}
	    	else
	    	{
	    		errorMessage=StringUtils.isEmpty(error)? "Unavaliable": error.toString();
	    	}
	    	model.addAttribute("status",StringUtils.isEmpty(status)? "Unavaliable": status.toString());
	    	model.addAttribute("error",errorMessage);
	    	//model.addAttribute("message","Please Contact System Admin.");

	        return "error";
	    }

	    @Override
	    public String getErrorPath() {
	        return PATH;
	    }

}
