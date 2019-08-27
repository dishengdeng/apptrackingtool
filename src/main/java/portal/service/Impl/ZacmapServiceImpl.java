package portal.service.Impl;


import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import portal.entity.Zacmap;
import portal.service.ZacmapService;
import portal.repository.ZacmapRepository;
@Service
public class ZacmapServiceImpl implements ZacmapService{

	@Autowired
	private ZacmapRepository zacmapRepository;
	
	@Override
	public Zacmap saveZacmap(Zacmap zacmap) {

		return zacmapRepository.save(zacmap);
	}

	@Override
	public Zacmap updateZacmap(Zacmap zacmap) {

		return zacmapRepository.saveAndFlush(zacmap);
	}

	@Override
	public void deleteZacmap(Zacmap zacmap) {
		
		zacmapRepository.delete(zacmap);
	}

	@Override
	public List<Zacmap> getAll() {
	
		return zacmapRepository.findAll();
	}

	@Override
	public void deleteAll(List<Zacmap> zacmap) {
		zacmapRepository.delete(zacmap);
		
	}



}
