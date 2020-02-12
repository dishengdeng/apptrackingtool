package portal.ImportDTO;

import java.util.concurrent.Callable;

import org.json.JSONObject;

import portal.entity.Appdepartment;

public class AppInventoryDTO implements Callable<Appdepartment>{

	private final JSONObject data;
	
	public AppInventoryDTO(final JSONObject _data)
	{
		this.data=_data;
	}	
	@Override
	public Appdepartment call() throws Exception {
		
		return null;
	}



}
