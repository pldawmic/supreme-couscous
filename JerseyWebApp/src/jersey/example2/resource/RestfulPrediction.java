package jersey.example2.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import jersey.example2.logic.PredictionsRS;

@ApplicationPath("/resourcesP")
public class RestfulPrediction extends Application {

	
	@Override
	public Set<Class<?>> getClasses() {
		
		Set<Class<?>> set = new HashSet<Class<?>>();
		set.add(PredictionsRS.class);
		
		return set;
	}
}
