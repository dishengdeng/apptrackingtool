package portal.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Set;

import org.json.JSONArray;


import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.Company;
import portal.entity.Contract;
import portal.entity.Department;
import portal.entity.License;
import portal.entity.Server;
import portal.entity.Site;
import portal.entity.Stakeholder;
import portal.entity.Support;
import portal.entity.Zac;
import portal.entity.Zone;
import portal.utility.JSONObjectWithEmpty;



public interface JsonWriter {
	public ByteArrayOutputStream writeJsonWithNoNull(JSONObjectWithEmpty reportModel) throws Exception;
	public ByteArrayInputStream writeJsonWithNoNullIn(JSONObjectWithEmpty reportModel) throws Exception;
	public JSONArray getStakeholders(Set<Stakeholder> stakeholders) throws Exception;
	public JSONArray getApplications(Set<Application> applications) throws Exception;
	public JSONArray getInstances(Set<AppInstance> appInstances) throws Exception;
	public JSONArray getDepartment(Set<Department> departments) throws Exception;
	public JSONArray getZones(Set<Zone> zones) throws Exception;
	public JSONArray getSites(Set<Site> sites) throws Exception;
	public JSONArray getVendors(Set<Company> vendors) throws Exception;
	public JSONArray getContracts(Set<Contract> contracts) throws Exception;
	public JSONArray getLicense(Set<License> licenses) throws Exception;
	public JSONArray getServers(Set<Server> servers) throws Exception;
	public JSONArray getSupports(Set<Support> supports) throws Exception;
	public JSONArray getZacs(Set<Zac> zacs) throws Exception;
}
