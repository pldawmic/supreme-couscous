package jaxws.metro.client.main;

import java.util.List;

import jaxws.metro.client.RandImplService;
import jaxws.metro.client.RandService;

public class RandClientMain {
	
	public static void main(String [] args) {
		
		RandImplService service = new RandImplService();
		RandService port = service.getRandImplPort();
		
		System.out.println(port.next1());
		
		List<Integer> nums = port.nextN(4);
		
		for(Integer num : nums) {
			System.out.println(num);
		}
	}
}
