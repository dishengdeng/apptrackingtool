package portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.AppInstance;
import portal.entity.SLA;
import portal.models.SLAModel;
import portal.repository.SLARepository;
import portal.service.SLAService;

@Service
public class SLAServiceImpl implements SLAService{

	@Autowired
	private SLARepository slaRepository;
	
	@Override
	public SLA addSLA(SLA sla) {

		return slaRepository.save(sla);
	}

	@Override
	public void delete(SLA sla) {

		slaRepository.delete(sla);
	}

	@Override
	public List<SLA> getAll() {

		return slaRepository.findAll();
	}

	@Override
	public List<SLAModel> getAllSLA() {
		List<SLAModel> slaModels = new ArrayList<SLAModel>();
		List<SLA> slas = slaRepository.findAll();
		for(SLA sla:slas)
		{
			SLAModel slaModel=new SLAModel();
			slaModel.setId(sla.getId());
			slaModel.setSlaName(sla.getSlaName());
			slaModel.setDescription(sla.getDescription());
			slaModel.setEffectivedate(sla.getEffectivedate().toString());
			slaModel.setTerminationdate(sla.getTerminationdate().toString());
			slaModel.setApprovername(sla.getApprovername());
			slaModel.setApprovaldate(sla.getApprovaldate().toString());
			slaModel.setDocreferece(sla.getDocreferece());
			slaModel.setAttachment(sla.getAttachment());
	

			
			slaModels.add(slaModel);
			
		}
		return slaModels;
	}

	@Override
	public SLA getByName(String slaName) {

		return slaRepository.findByName(slaName);
	}

	@Override
	public SLA getById(Long id) {

		return slaRepository.findOne(id);
	}

	@Override
	public SLA updateSLA(SLA sla) {

		return slaRepository.saveAndFlush(sla);
	}

	@Override
	public void updateAppIstanceSLA(List<AppInstance> appInstances, SLA sla) {
		
		slaRepository.removeAllSLA(sla);
		if(appInstances.size()>0)
		{
			for(AppInstance appInstance:appInstances)
			{
				slaRepository.updateAppIstanceSLA(appInstance.getId(), sla);
			}
		}
	}

	@Override
	public void removeAllSLA(SLA sla) {
		
		slaRepository.removeAllSLA(sla);
	}




}
