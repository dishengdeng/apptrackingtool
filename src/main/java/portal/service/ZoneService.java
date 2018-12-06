package portal.service;

import java.util.List;


import portal.entity.Zone;
import portal.models.ZoneModel;


public interface ZoneService {
	public Zone addZone(Zone zone);
    void delete(Zone zone);
    List<Zone> getAll();
    List<ZoneModel> getAllZone();
    Zone getByName(String zoneName);
    Zone getById(Long id);
    Zone updateZone(Zone zone);	
}
