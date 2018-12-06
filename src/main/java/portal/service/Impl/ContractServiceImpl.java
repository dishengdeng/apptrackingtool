package portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.AppInstance;
import portal.entity.Contract;
import portal.models.ContractModel;
import portal.repository.ContractRepository;
import portal.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService{

	@Autowired
	private ContractRepository contractRepository;
	
	@Override
	public Contract addContract(Contract contract) {

		return contractRepository.save(contract);
	}

	@Override
	public void delete(Contract contract) {
		
		contractRepository.delete(contract);
		
	}

	@Override
	public List<Contract> getAll() {

		return contractRepository.findAll();
	}

	@Override
	public List<ContractModel> getAllContract() {
		
		List<ContractModel> contractModels = new ArrayList<ContractModel>();
		List<Contract> contracts = contractRepository.findAll();
		
		for(Contract contract:contracts)
		{
			ContractModel contractModel = new ContractModel();
			contractModel.setId(contract.getId());
			contractModel.setContractName(contract.getContractName());
			contractModel.setDescription(contract.getDescription());
			contractModel.setExpireDate(contract.getExpireDate().toString());
			contractModel.setSlaDescription(contract.getSlaDescription());
			contractModel.setSmaDescription(contract.getSmaDescription());
			contractModels.add(contractModel);
		}
		return contractModels;
	}

	@Override
	public Contract getByName(String contractName) {

		return contractRepository.findByName(contractName);
	}

	@Override
	public Contract getById(Long id) {

		return contractRepository.findOne(id);
	}

	@Override
	public Contract updateContract(Contract contract) {

		return contractRepository.saveAndFlush(contract);
	}

	@Override
	public void removeAppInstance(AppInstance appInstance) {
		
		contractRepository.removeAppInstance(appInstance);
	}

	@Override
	public void updateAppInstance(AppInstance appInstance, Long id) {
		contractRepository.updateAppInstance(appInstance, id);
		
	}

	@Override
	public List<Contract> findByNotAssigned(AppInstance appInstance) {
		
		List<Contract> contracts = contractRepository.findAll();
		contracts.removeIf(obj->appInstance.equals(obj.getAppInstance()) || obj.getAppInstance()!=null);
	
		
		return contracts;
	}

	@Override
	public Contract findByAppInstance(AppInstance appInstance) {
		
		return contractRepository.findByAppInstance(appInstance);
	}

}
