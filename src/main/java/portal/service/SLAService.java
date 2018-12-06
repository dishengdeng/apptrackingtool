package portal.service;

import java.util.List;

import portal.entity.AppInstance;
import portal.entity.SLA;
import portal.models.SLAModel;



public interface SLAService {
	public SLA addSLA(SLA sla);
    void delete(SLA sla);
    List<SLA> getAll();
    List<SLAModel> getAllSLA();
    SLA getByName(String slaName);
    SLA getById(Long id);
    SLA updateSLA(SLA sla);
    SLA findByAppInstance(AppInstance appInstance);
    List<SLA> findByNotAssigned(AppInstance appInstance);
    void removeAppInstance(AppInstance appInstance);
    void updateAppInstance(AppInstance appInstance,Long id);

}
