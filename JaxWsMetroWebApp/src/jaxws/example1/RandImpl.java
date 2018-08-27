package jaxws.example1;

import java.util.Random;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface="jaxws.example1.RandService")
public class RandImpl implements RandService {

	private static final int maxRands = 16;
	
	@WebMethod
	public int next1() {
		return new Random().nextInt();
	}

	@WebMethod
	public int[] nextN(final int n) {
		
		final int k = (n > maxRands) ? maxRands : Math.abs(n);
		int rands [] = new int[k];
		
		Random rand = new Random();
		
		for (int i = 0; i < k; i++) {
			rands[i] = rand.nextInt();
		}
		
		return rands;
	}
}
