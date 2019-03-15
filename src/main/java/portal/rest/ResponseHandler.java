package portal.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class ResponseHandler<T> extends ResponseEntity<T>
{

	public ResponseHandler(HttpStatus status) {
		super(status);

		
	}
	
	public ResponseHandler(T body,HttpStatus status) {
		super(body, null, status);
	}
	
	public ResponseHandler(T body, MultiValueMap<String, String> headers, HttpStatus status) {
		super(body, headers, status);
	}
	
}
