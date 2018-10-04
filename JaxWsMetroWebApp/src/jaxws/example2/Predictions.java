package jaxws.example2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;

public class Predictions {
	
	private ConcurrentMap<Integer, Prediction> predictions;
	private ServletContext servletCtx;
	private AtomicInteger mapKey;
	
	public Predictions() {
		
		predictions = new ConcurrentHashMap<Integer, Prediction>();
		mapKey = new AtomicInteger();
	}
	
	public void setServletContext(ServletContext servletCtx) {
		this.servletCtx = servletCtx;
	}
	
	public ServletContext getServletContext() {
		return this.servletCtx;
	}
	
	public ConcurrentMap<Integer, Prediction> getMap() {
		
		if (good2Go()) {
			return this.predictions;
		} else {
			return null;
		}
	}
	
	public int addPrediction(Prediction p) {
		
		int id = mapKey.incrementAndGet();
		p.setId(id);
		predictions.putIfAbsent(id, p);
		
		return id;
	}
	
	public Prediction getPrediction(int id) {
		return predictions.get(id);
	}
	
	public List<Prediction> getPredictions() {
		
		List<Prediction> predictionList;
		
		if (good2Go()) {
			
			Object [] preds = predictions.values().toArray();
			Arrays.sort(preds);
			
			predictionList = new ArrayList<>();
			
			for (Object obj : preds) {
				predictionList.add((Prediction)obj);
			}
			
			return predictionList;
		} else {
			return null;
		}
		
	}
	
	private boolean good2Go() {
		
		if (getServletContext() == null) {
			return false;
		}
		
		if (predictions.size() < 1) {
			populate();
		}
		
		return true;
	}
	
	private void populate() {
		
		String filename = "/WEB-INF/data/predictions.db";
		InputStream in = servletCtx.getResourceAsStream(filename);
		
		if (in != null) {
			
			InputStreamReader inputStreamReader = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			
			String record = null;
			
			try {
				
				while ((record = reader.readLine()) != null) {
					
					String parts [] = record.split("!");
					
					Prediction p = new Prediction();
					p.setWho(parts[0]);
					p.setWhat(parts[1]);
					
					addPrediction(p);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
