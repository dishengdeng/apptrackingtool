package portal.service;

import java.util.List;

import portal.entity.Support;
import portal.models.SupportModel;




public interface SupportService {
	public Support addSupport(Support support);
    void delete(Support support);
    List<Support> getAll();
    List<SupportModel> getAllSupport();
    Support getByName(String supportName);
    Support getById(Long id);
    Support updateSupport(Support support);	
}
