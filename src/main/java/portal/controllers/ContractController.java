package portal.controllers;



import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import portal.entity.Contract;
import portal.service.AppInstanceService;
import portal.service.AppService;
import portal.service.ContractService;
import portal.service.FileService;




@Controller
public class ContractController {
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private AppInstanceService appInstanceService;
	
	@Autowired
	private AppService appService;		
	
	@Autowired
	private FileService fileService;
	
	private final String UPLOADED_FOLDER="files//contract//";
	
    @GetMapping("/contracts")
    public String contracttable(ModelMap model) {
    	model.addAttribute("contracts", contractService.getAll());
    	model.addAttribute("appUnassginedInstances",appInstanceService.getUnassginedAppInstances());
    	model.addAttribute("appAssginedInstances",appService.getAll().stream().sorted().collect(Collectors.toList()));
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

    	contractService.updateAppInstanceContract(contract.getAppInstances(), contract);
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
    	
    	contractService.removeAllContract(contract);
    	
    	contractService.delete(contract);
    	return "redirect:/contracts";
    }

  //------file management----
    @PostMapping("/contractupload")
    public String uploadContract(@RequestParam("file") MultipartFile file,@RequestParam("contractid") String id) {
    	Contract contract= contractService.getById(Long.valueOf(id));


		if(fileService.uploadFile(file, UPLOADED_FOLDER,id));
		{
			contract.setAttachment(fileService.getFileName(file.getOriginalFilename()));
			contractService.updateContract(contract);
		}
		
        return "redirect:/contracts";
    }
    
    @GetMapping("/downloadcontract")
    public ResponseEntity<Resource> downloadfile(@RequestParam(name="id", required=true) String id,@RequestParam(name="filename", required=true) String filename,HttpServletRequest request)
    {
    	

    	return fileService.downloadFile(UPLOADED_FOLDER, id, filename, request);
    }

    @GetMapping("/deletecontractfile")
    public String deletefile(@RequestParam(name="id", required=true) String id,@RequestParam(name="filename", required=true) String filename)
    {
    	
    	Contract contract= contractService.getById(Long.valueOf(id));
    	
		if(fileService.removeFile(UPLOADED_FOLDER, id, filename));
		{
			contract.removeAttachment(filename);
			contractService.updateContract(contract);
		}
		return "redirect:/contracts";
    	
    }
}
