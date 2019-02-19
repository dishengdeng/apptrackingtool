package portal.service;

import java.util.List;

import portal.entity.SLARole;
import portal.entity.Stakeholder;
import portal.models.SLARoleModel;




public interface SLARoleService {
	public SLARole addSLARole(SLARole SLARole);
    void delete(SLARole SLARole);
    List<SLARole> getAll();
    List<SLARoleModel> getAllSLARole();
    SLARole getByName(String SLARoleName);
    SLARole getById(Long id);
    SLARole updateSLARole(SLARole SLARole);
    void updateSLARoleStakeholder(List<Stakeholder> stakeholders,SLARole slarole);
}
