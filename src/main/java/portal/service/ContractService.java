package portal.service;

import java.util.List;


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

    void removFiles(String upload_foler,Contract contract);
}
