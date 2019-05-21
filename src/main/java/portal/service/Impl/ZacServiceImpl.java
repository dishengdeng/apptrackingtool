package portal.service.Impl;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import portal.entity.Zac;
import portal.repository.ZacRepository;
import portal.service.ZacService;
@Service
public class ZacServiceImpl implements ZacService{

	@Autowired
	private ZacRepository zacRepository;
	
	@Override
	public Zac saveZac(Zac zac) {
		
		return zacRepository.save(zac);
	}

	@Override
	public Zac updateZac(Zac zac) {
		
		return zacRepository.saveAndFlush(zac);
	}

	@Override
	public void deleteZac(Zac zac) {
		zacRepository.delete(zac);
		
	}

	@Override
	public List<Zac> getAll() {
		
		return zacRepository.findAll();
	}

}
