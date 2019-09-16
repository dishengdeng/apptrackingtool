package portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Site;
import portal.entity.Zone;
import portal.models.SiteModel;
import portal.models.ZoneModel;
import portal.repository.SiteRepository;
import portal.service.SiteService;
@Service
public class SiteServiceImpl implements SiteService{

	@Autowired
	private SiteRepository siteRepository;
	@Override
	public Site addSite(Site site) {
		
		return siteRepository.save(site);
	}

	@Override
	public void delete(Site site) {
		siteRepository.delete(site);
		
	}

	@Override
	public List<Site> getAll() {

		return siteRepository.findAll();
	}

	@Override
	public List<SiteModel> getAllSite() {
		List<SiteModel> siteModels = new ArrayList<SiteModel>();
		List<Site> sites= siteRepository.findAll();
		for(Site site:sites)
		{
			SiteModel model=new SiteModel();
			
			ZoneModel zonemodel= new ZoneModel();
			zonemodel.setId(site.getZone().getId());
			zonemodel.setZoneName(site.getZone().getZoneName());
			zonemodel.setNote(site.getZone().getNote());
			zonemodel.setDescription(site.getZone().getDescription());
			
			model.setId(site.getId());
			model.setSiteName(site.getSiteName());
			model.setDescription(site.getDescription());
			model.setNote(site.getNote());
			model.setZone(zonemodel);
			
			siteModels.add(model);
		}
		return siteModels;
	}

	@Override
	public Site getByName(String siteName) {
		
		return siteRepository.findByName(siteName);
	}

	@Override
	public Site getById(Long id) {

		return siteRepository.findOne(id);
	}

	@Override
	public Site updateSite(Site site) {
		
		return siteRepository.saveAndFlush(site);
	}

	@Override
	public List<Site> getAllbyZone(Zone zone) {
		
		return siteRepository.findAllbyZone(zone);
	}



}
