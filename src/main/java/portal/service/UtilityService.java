package portal.service;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public interface UtilityService {
    public JSONObject getJSONObject(HttpServletRequest request) throws Exception;
}
