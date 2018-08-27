package jaxws.example1;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface RandService {
	
	@WebMethod
	public int next1();
	
	@WebMethod
	public int [] nextN(final int n);
}
