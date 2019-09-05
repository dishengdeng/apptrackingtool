package portal.service;

import java.util.List;


import portal.entity.Stakeholder;

import portal.models.StakeholderModel;




public interface StakeholderService {
	public Stakeholder addStakeholder(Stakeholder stakeholder);
    void delete(Stakeholder stakeholder);
    List<Stakeholder> getAll();
    List<StakeholderModel> getAllStakeholder();
    Stakeholder getByName(String stakeholderName);
    Stakeholder getById(Long id);
    Stakeholder updateStakeholder(Stakeholder stakeholder);	

    public void updateDetail(StakeholderModel stakeholdeModel);
}
