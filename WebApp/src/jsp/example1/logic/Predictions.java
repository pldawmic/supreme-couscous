package jsp.example1.logic;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;

import jsp.example1.pojo.Prediction;

public class Predictions {
	
	private int n = 32;
	private Prediction [] predictions;
	private ServletContext sctx;
	
	public Predictions() {}
	
	public void setServletContext(ServletContext sctx) {this.sctx = sctx;}
	public ServletContext getServletContext() {return this.sctx;}
	
	public String getPredictions() throws IOException {
		
		if(getServletContext() == null) {
			return null;
		}
		
		if(predictions == null) {
			populate();
		}
		
		return toXML();
	}
	
	private void populate() throws IOException {

		String fileName = "/WEB-INF/data/predictions.db";
		InputStream in = sctx.getResourceAsStream(fileName);

		if (in != null) {
			
			InputStreamReader isr = new InputStreamReader(in, "UTF-8");
			BufferedReader reader = new BufferedReader(isr);
			
			predictions = new Prediction[n];
			int i = 0;
			String record = null;
			
			while((record = reader.readLine()) != null) {
				
				String [] parts = record.split("!");
				
				Prediction p = new Prediction();
				p.setWho(parts[0]);
				p.setWhat(parts[1]);
				
				predictions[i++] = p;
			}
		}

	}
	
	private String toXML() {
		
		String xml = null;
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(out, "UTF-8", false, 1);
		encoder.writeObject(predictions);
		encoder.close();
		
		try {
			xml = out.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return xml;
	}
}
