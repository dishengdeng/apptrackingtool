package portal.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import portal.models.Alert;

@RestController
@RequestMapping("/api")
public class AlertRestController {
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@RequestMapping(value = "/alert", method = RequestMethod.POST)
	public ResponseEntity<Alert> apilogin(@RequestBody Alert alert)
	{
		if(!ObjectUtils.isEmpty(alert.getTitle())) 
		{
			this.messagingTemplate.convertAndSend("/subject/alert", alert);
			//this.messagingTemplate.convertAndSendToUser("kevin","/subject/alert", alert);
			return new ResponseEntity<Alert>(alert,HttpStatus.OK);
		}
		
		return new ResponseEntity<Alert>(HttpStatus.BAD_REQUEST);
	}

}
