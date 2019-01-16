package portal.service;

import java.util.List;

import portal.entity.AppInstance;
import portal.entity.Contract;
import portal.models.ContractModel;




public interface ContractService {
	public Contract addContract(Contract contract);
    void delete(Contract contract);
    List<Contract> getAll();
    List<ContractModel> getAllContract();
    Contract getByName(String contractName);
    Contract getById(Long id);
    Contract updateContract(Contract contract);
    void updateAppInstanceContract(List<AppInstance> appInstances,Contract contract);
    void removeAllContract(Contract contract);

}
