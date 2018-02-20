package jersey.example1.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import jersey.example1.logic.Adages;

@ApplicationPath("/resourcesA")
public class RestfulAdage  extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
		
		Set<Class<?>> set = new HashSet<Class<?>>();
		set.add(Adages.class);
		
		return set;
	}
}
