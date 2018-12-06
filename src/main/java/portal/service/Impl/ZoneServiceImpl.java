package portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Zone;
import portal.models.ZoneModel;
import portal.repository.ZoneRepository;
import portal.service.ZoneService;

@Service
public class ZoneServiceImpl implements ZoneService{
	@Autowired
	private ZoneRepository zoneRepository;
	@Override
	public Zone addZone(Zone zone) {
		
		return zoneRepository.save(zone);
	}

	@Override
	public void delete(Zone zone) {
		zoneRepository.delete(zone);
		
	}

	@Override
	public List<Zone> getAll() {
		
		return zoneRepository.findAll();
	}

	@Override
	public List<ZoneModel> getAllZone() {
		List<ZoneModel> zoneModels = new ArrayList<ZoneModel>();
		List<Zone> zones = zoneRepository.findAll();
		for(Zone zone:zones)
		{
			ZoneModel model = new ZoneModel();
			model.setId(zone.getId());
			model.setZoneName(zone.getZoneName());
			model.setDescription(zone.getDescription());
			model.setNote(zone.getNote());
			zoneModels.add(model);
		}
		return zoneModels;
	}

	@Override
	public Zone getByName(String zoneName) {
		
		return zoneRepository.findByName(zoneName);
	}

	@Override
	public Zone updateZone(Zone zone) {
		
		return zoneRepository.saveAndFlush(zone);
	}

	@Override
	public Zone getById(Long id) {
		
		return zoneRepository.findOne(id);
	}

}
