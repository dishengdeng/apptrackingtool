package portal.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Stakeholderext;
import portal.repository.StakeholderextRepository;
import portal.service.StakeholderextService;
@Service
public class StakeholderextServiceImpl implements StakeholderextService{

	@Autowired
	private StakeholderextRepository stakeholderextRepository;
	@Override
	public Stakeholderext save(Stakeholderext stakeholderext) {

		return stakeholderextRepository.save(stakeholderext);
	}

	@Override
	public Stakeholderext update(Stakeholderext stakeholderext) {

		return stakeholderextRepository.saveAndFlush(stakeholderext);
	}

	@Override
	public void delete(Stakeholderext stakeholderext) {
		stakeholderextRepository.delete(stakeholderext);
		
	}

	@Override
	public List<Stakeholderext> getAll() {

		return stakeholderextRepository.findAll();
	}

}
