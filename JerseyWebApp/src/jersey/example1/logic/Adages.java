package jersey.example1.logic;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jersey.example1.pojo.Adage;

@Path("/")
public class Adages {
	
	private String [] aphorisms = {
				"What can be shown cannot be said.", 
				"If a lion could talk, we could not understand him.",
				"Philosophy is a battle against the bewitchment of our intelligence by means of language.",
				"Ambition is the death of thought.",
				"The limits of my language mean the limits of my world."
	};
	
	public Adages() {}
	
	
	@GET
	@Produces({MediaType.TEXT_PLAIN})
	public String getPlain() {
		return createAdage().toString() + "\n";
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/json")
	public String getJson() {
		return toJson(createAdage());
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML})
	@Path("/xml")
	public JAXBElement<Adage> getXml() {
		return toXml(createAdage());
	}
	
	private Adage createAdage() {
		
		Adage adage = new Adage();
		adage.setWords(aphorisms[new Random().nextInt(aphorisms.length)]);
		
		return adage;
	}
	
	private JAXBElement<Adage> toXml(Adage adage) {
		return new JAXBElement<Adage>(new QName("adage"), Adage.class, adage);
	}
	
	private String toJson(Adage adage) {
		
		String json = "Json error occured.";
		
		try {
			json = new ObjectMapper().writeValueAsString(adage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
}
