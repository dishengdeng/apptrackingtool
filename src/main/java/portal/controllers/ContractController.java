package portal.controllers;






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
import portal.entity.File;
import portal.service.ContractService;
import portal.service.FileService;
import portal.utility.FileType;




@Controller
public class ContractController {
	@Autowired
	private ContractService contractService;
	
	
	
	@Autowired
	private FileService fileService;
	
	private final String UPLOADED_FOLDER="files//contract//";
	
    @GetMapping("/contracts")
    public String contracttable(ModelMap model) {
    	model.addAttribute("contracts", contractService.getAll());

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
    	return "redirect:/contractdetail?contract="+contract.getId();
    }
    
    @GetMapping("/addContract")
    public String CreateContract(ModelMap model) {
    	model.addAttribute("contract", new Contract());
        return "addContract";
    }
    
    @GetMapping("/deleteContract")
    public String deleteContract(@ModelAttribute("contract") Contract contract) {

    	
    	contract.removeAllDependence();
    	contractService.removFiles(UPLOADED_FOLDER, contract);
    	contractService.delete(contract);
    	return "redirect:/contracts";
    }
    
    @GetMapping("/contractdetail")
    public String supportdetail(@ModelAttribute("contract") Contract contract,ModelMap model) {
    	model.addAttribute("contract",contract);

        return "contractdetail";
    } 
  //------file management----
    @PostMapping("/contractupload")
    public String uploadContract(@RequestParam("file") MultipartFile file,@ModelAttribute("contract") Contract contract) {


    	File fileEntity = new File();
    	fileEntity.setFiletype(FileType.CONTRACT);
    	fileEntity.setAttachment(fileService.getFileName(file.getOriginalFilename()));
    	fileEntity.setContract(contract);

		fileService.uploadFile(file, UPLOADED_FOLDER,contract.getId().toString(),fileEntity);
		
        return "redirect:/contractdetail?contract="+contract.getId();
    }
    
    @GetMapping("/downloadcontract")
    public ResponseEntity<Resource> downloadfile(@ModelAttribute("file") File file,HttpServletRequest request)
    {
    	


    	return fileService.downloadFile(UPLOADED_FOLDER,file.getContract().getId().toString(),file , request);
    }

    @GetMapping("/deletecontractfile")
    public String deletefile(@ModelAttribute("file") File file,@ModelAttribute("contract") Contract contract)
    {
    	

    	
		fileService.removeFile(UPLOADED_FOLDER, file.getContract().getId().toString(),file);
		return "redirect:/contractdetail?contract="+contract.getId();
    	
    }
}
