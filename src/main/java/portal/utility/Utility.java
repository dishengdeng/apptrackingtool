package portal.utility;



import org.springframework.stereotype.Service;


@Service
public class Utility<T> {
	public boolean isNull(T obj)
	{
		
		return obj ==null ? true:false;
	}

}
