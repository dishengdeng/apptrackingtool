package portal.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import portal.entity.Contract;

import portal.service.ContractService;




@Controller
public class ContractController {
	@Autowired
	private ContractService contractService;
	
	
    @GetMapping("/contracts")
    public String contracttable(ModelMap model) {
    	model.addAttribute("contracts", contractService.getAll());
    	model.addAttribute("contractModel", new Contract());
        return "contracts";
    }
    
    @PostMapping("/addContract")
    public String addContract(@ModelAttribute("contract") Contract contract) {

    	contractService.addContract(contract);
        return "redirect:/contracts";
    }
 
    @PostMapping("/updateContract")
    public String updateContract(@ModelAttribute("contractModel") Contract contract) {

    	contractService.updateContract(contract);
        return "redirect:/contracts";
    }
    
    @GetMapping("/addContract")
    public String CreateContract(ModelMap model) {
    	model.addAttribute("contract", new Contract());
        return "addContract";
    }
    
    @GetMapping("/deleteContract")
    public String deleteContract(@RequestParam(name="id", required=true) String id,@RequestParam(name="contractName", required=true) String contractName) {
    	Contract contract = new Contract();
    	contract.setId(Long.valueOf(id));
    	contract.setContractName(contractName);
    	

    	
    	contractService.delete(contract);
    	return "redirect:/contracts";
    }


}
