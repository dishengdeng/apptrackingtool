package portal.utility;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectWithEmpty extends JSONObject{
	
	public JSONObjectWithEmpty(String jsonString)
	{
		super(jsonString);
	}
	
	public JSONObjectWithEmpty()
	{
		super();
	}	

	@Override
	public JSONObject put(String arg0, boolean arg1) throws JSONException {
		
		return super.put(arg0, Convertor.getEmpty(arg1));
	}

	@Override
	public JSONObject put(String arg0, double arg1) throws JSONException {
		
		return super.put(arg0, Convertor.getEmpty(arg1));
	}

	@Override
	public JSONObject put(String arg0, int arg1) throws JSONException {
		
		return super.put(arg0, Convertor.getEmpty(arg1));
	}

	@Override
	public JSONObject put(String arg0, long arg1) throws JSONException {
		
		return super.put(arg0, Convertor.getEmpty(arg1));
	}

	@Override
	public JSONObject put(String arg0, Object arg1) throws JSONException {
		
		return super.put(arg0, Convertor.getEmpty(arg1));
	}

}
