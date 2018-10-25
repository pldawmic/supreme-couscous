package jaxws.metro.client2.main;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;

import jaxws.metro.client2.NextNResponse;
import jaxws.metro.client2.RandImplService;
import jaxws.metro.client2.RandService;

public class RandClientAsync {
	
	public static void main(String [] args) {
		
		RandImplService service = new RandImplService();
		RandService port = service.getRandImplPort();
		
		port.nextNAsync(4, new MyHandler());
		
		try {
			Thread.sleep(5000);
		} catch (Exception e) {}
		
		System.out.println("\nmain is exiting");
	}
	
	static class MyHandler implements AsyncHandler<NextNResponse> {

		@Override
		public void handleResponse(Response<NextNResponse> future) {
			
			try {
				NextNResponse response = future.get();
				List<Integer> nums = response.getReturn();
				for(Integer num : nums) {
					System.out.println(num);
				}
				
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}
