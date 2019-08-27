package portal.service.Impl;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import portal.entity.Zaclist;
import portal.repository.ZaclistRepository;
import portal.service.ZaclistService;
@Service
public class ZaclistServiceImpl implements ZaclistService{

	@Autowired
	private ZaclistRepository zacRepository;

	@Override
	public Zaclist saveZaclist(Zaclist zaclist) {

		return zacRepository.save(zaclist);
	}

	@Override
	public Zaclist updateZaclist(Zaclist zaclist) {

		return zacRepository.saveAndFlush(zaclist);
	}

	@Override
	public void deleteZaclist(Zaclist zaclist) {
		zacRepository.delete(zaclist);
		
	}

	@Override
	public List<Zaclist> getAll() {

		return zacRepository.findAll();
	}
	
	
}
