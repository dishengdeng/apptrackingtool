package portal.rest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import portal.entity.Zone;
import portal.models.ZoneModel;
import portal.service.ZoneService;

@RestController
@RequestMapping("/api")
public class ZoneRestController {
	@Autowired
	private ZoneService zoneService;
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;	
	

	@RequestMapping(value = "/zones", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<ZoneModel> getAllZones()
	{
		return zoneService.getAllZone();
	}
	
	@RequestMapping(value = "/zone/create", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public Zone createZone(@RequestBody Zone zone)
	{
		Zone zoneEntity=zoneService.addZone(zone);
		messagingTemplate.convertAndSend("/subject/zone",zoneEntity);
		return zoneEntity;
	}
	
	@RequestMapping(value = "/zone/update", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public Zone updateZone(@RequestBody Zone zone)
	{
		Zone zoneEntity= zoneService.getById(zone.getId());
		zoneEntity.setZoneName(zone.getZoneName());
		zoneEntity.setNote(zone.getNote());
		zoneEntity.setDescription(zone.getDescription());
		zoneService.updateZone(zoneEntity);
		return zone;
	}

}
