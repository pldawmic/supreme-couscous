package jaxws.metro.client3.handler;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class ClientHandlerResolver implements HandlerResolver {

	private String name;
	private String key;
	
	public ClientHandlerResolver(String name, String key) {
		
		this.name = name;
		this.key = key;
	}

	@SuppressWarnings("rawtypes")
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		
		List<Handler> handlerChain = new ArrayList<Handler>();
		
		handlerChain.add(new ClientHashHandler(this.name, this.key));
		
		return handlerChain;
	}
}
