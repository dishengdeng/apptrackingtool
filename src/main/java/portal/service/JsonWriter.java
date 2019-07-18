package portal.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import portal.entity.AppInstance;
import portal.entity.Application;
import portal.entity.Department;
import portal.entity.Stakeholder;



public interface JsonWriter {
	public ByteArrayOutputStream writeJsonWithNoNull(JSONObject reportModel) throws Exception;
	public ByteArrayInputStream writeJsonWithNoNullIn(JSONObject reportModel) throws Exception;
	public JSONArray getStakeholders(Set<Stakeholder> stakeholders) throws Exception;
	public JSONArray getApplications(Set<Application> applications) throws Exception;
	public JSONArray getInstances(Set<AppInstance> appInstances) throws Exception;
	public JSONArray getDepartment(Set<Department> departments) throws Exception;
}
