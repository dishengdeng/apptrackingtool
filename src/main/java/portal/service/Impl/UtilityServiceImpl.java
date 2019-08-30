package portal.service.Impl;

import java.io.BufferedReader;


import javax.servlet.http.HttpServletRequest;



import org.json.JSONObject;
import org.springframework.stereotype.Service;

import portal.service.UtilityService;
@Service
public class UtilityServiceImpl implements UtilityService{

	@Override
	public JSONObject getJSONObject(HttpServletRequest request) throws Exception {
		StringBuffer jb = new StringBuffer();
	  	String line = null;
	  	BufferedReader reader = request.getReader();
	  	while ((line = reader.readLine()) != null)
	  	jb.append(line);
		return new JSONObject(jb.toString());
	}

}
