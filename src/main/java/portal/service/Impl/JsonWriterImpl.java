package portal.service.Impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;


import portal.models.ReportModel;
import portal.service.JsonWriter;

@Service
public class JsonWriterImpl implements JsonWriter{



	@Override
	public ByteArrayOutputStream writeJsonWithNoNull(ReportModel reportModel) throws Exception{

    	ByteArrayOutputStream output=new ByteArrayOutputStream();
    	getMapper().writeValue(output, reportModel);
    	
    	return output;
		
	}
	
	private class NullSerializer extends JsonSerializer<Object>
	{
	   public void serialize(Object value, JsonGenerator jgen,	SerializerProvider provider) throws IOException, JsonProcessingException
	   {

	       jgen.writeString("");
	   }
	}
	
	private ObjectMapper getMapper()
	{
		DefaultSerializerProvider.Impl sp = new DefaultSerializerProvider.Impl();
		sp.setNullValueSerializer(new NullSerializer());
    	ObjectMapper mapper=new ObjectMapper();
    	mapper.setSerializerProvider(sp);
    	return mapper;
	}

	@Override
	public ByteArrayInputStream writeJsonWithNoNullIn(ReportModel reportModel) throws Exception {
		ByteArrayInputStream input= new ByteArrayInputStream(getMapper().writeValueAsBytes(reportModel));
		return input;
	}


}
