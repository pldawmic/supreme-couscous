package jersey.example2.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jersey.example2.pojo.Prediction;

@Path("/")
public class PredictionsRS {
	
	@Context
	private ServletContext sctx;
	private static PredictionsList pList;
	
	public PredictionsRS() {}
	
	@GET
	@Path("/xml")
	@Produces({MediaType.APPLICATION_XML})
	public Response getXml() {
		
		checkContext();
		return Response.ok(pList, "application/xml").build();
	}
	
	@GET
	@Path("/xml/{id: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public Response getXml(@PathParam("id") int id) {
		
		checkContext();
		return toRequestType(id, "application/xml");
	}
	
	@GET
	@Path("/json")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getJson() {
		
		checkContext();
		return Response.ok(toJson(pList), "application/json").build();
	}
	
	@GET
	@Path("/json/{id: \\d+}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getJson(@PathParam("id")int id) {
		
		checkContext();
		return toRequestType(id, "application/json");
	}
	
	@GET
	@Path("/plain")
	@Produces({MediaType.TEXT_PLAIN})
	public String getPlain() {
		
		checkContext();
		return pList.toString();
	}
	
	@POST
	@Path("/create")
	@Produces({MediaType.TEXT_PLAIN})
	public Response create(@FormParam("who") String who, @FormParam("what") String what) {
		
		checkContext();
		String msg = null;
		
		if (who == null || what == null) {
			msg = "Property 'who' or 'what' is missing.\n";
			return Response.status(Response.Status.BAD_REQUEST).entity(msg).type(MediaType.TEXT_PLAIN).build();
		}
		
		int id = pList.add(who, what);
		msg = "Prediction " + id + " created(who =" + who + " what = " + what + ").\n";
		
		return Response.ok(msg, "text/plain").build();
	}
	
	private void checkContext() {
		if (pList == null) {
			populate();
		}
	}
	
	private Response toRequestType(int id, String type) {
		
		Prediction prediction = pList.find(id);
		if(prediction == null) {
			String msg = id + " is a bad ID.\n";
			return Response.status(Response.Status.BAD_REQUEST).entity(msg).type(MediaType.TEXT_PLAIN).build();
		} else if(type.contains("json")) {
			return Response.ok(toJson(prediction), type).build();
		} else {
			return Response.ok(prediction, type).build();
		}
	}
	
	private String toJson(Prediction prediction) {
		
		String json = "";
		
		try {
			json = new ObjectMapper().writeValueAsString(prediction);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	private String toJson(PredictionsList predictionList) {
		
		String json = "";
		
		try {
			json = new ObjectMapper().writeValueAsString(predictionList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	private void populate() {
		
		pList = new PredictionsList();
		
		String fileName = "/WEB-INF/data/predictions.db";
		InputStream in = sctx.getResourceAsStream(fileName);
		
		if(in != null) {
			
			try {
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String record = null;
			
				while(( record = reader.readLine()) != null) {
					String [] parts = record.split("!");
					addPrediction(parts[0], parts[1]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private int addPrediction(String who, String what)  {
		return pList.add(who, what);
	}
 }
