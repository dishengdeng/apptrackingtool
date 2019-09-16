package portal.service;

import java.util.List;


import portal.entity.Site;
import portal.entity.Zone;
import portal.models.SiteModel;



public interface SiteService {
	public Site addSite(Site site);
    void delete(Site site);
    List<Site> getAll();
    List<SiteModel> getAllSite();
    Site getByName(String siteName);
    Site getById(Long id);
    Site updateSite(Site site);
    List<Site> getAllbyZone(Zone zone);

}
