package portal.service.Impl;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import portal.entity.Stakeholder;
import portal.models.StakeholderModel;
import portal.repository.SiteRepository;
import portal.repository.StakeholderRepository;
import portal.service.StakeholderService;


@Service
public class StakeholderServiceImpl implements StakeholderService{

	@Autowired
	private StakeholderRepository stakeholderRepository;
	
	@Autowired
	private SiteRepository siteRepository;
	@Override
	public Stakeholder addStakeholder(Stakeholder stakeholder) {

		return stakeholderRepository.save(stakeholder);
	}

	@Override
	public void delete(Stakeholder stakeholder) {

		stakeholderRepository.delete(stakeholder);
	}

	@Override
	public List<Stakeholder> getAll() {

		return stakeholderRepository.findAll();
	}

	@Override
	public List<StakeholderModel> getAllStakeholder() {

		List<StakeholderModel> stakeholderModels = new ArrayList<StakeholderModel>();
		List<Stakeholder> stakeholders = stakeholderRepository.findAll();
		for(Stakeholder stakeholder:stakeholders)
		{
			StakeholderModel stakeholderModel = new StakeholderModel();
			stakeholderModel.setEmail(stakeholder.getEmail());
			stakeholderModel.setNote(stakeholder.getNote());
			stakeholderModel.setPosition(stakeholder.getPosition());
			stakeholderModel.setStakeholderName(stakeholder.getStakeholderName());
			stakeholderModels.add(stakeholderModel);
		}
		return stakeholderModels;
	}

	@Override
	public Stakeholder getByName(String stakeholderName) {
		
		return stakeholderRepository.findByName(stakeholderName);
	}

	@Override
	public Stakeholder getById(Long id) {

		return stakeholderRepository.findOne(id);
	}

	@Override
	public Stakeholder updateStakeholder(Stakeholder stakeholder) {

		return stakeholderRepository.saveAndFlush(stakeholder);
	}



	@Override
	public void updateDetail(StakeholderModel stakeholderModel) {

		stakeholderRepository.updateDetail(stakeholderModel.getStakeholderName(),stakeholderModel.getNote(),
				ObjectUtils.isEmpty(stakeholderModel.getSite())? null: siteRepository.findOne(stakeholderModel.getSite()), stakeholderModel.getPhone(), 
				stakeholderModel.getPosition(),stakeholderModel.getBusinessunit(), stakeholderModel.getEmail(), 
				stakeholderModel.getId());
		

		
	}

}
