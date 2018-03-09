package jersey.example2.logic;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import jersey.example2.pojo.Prediction;

@XmlRootElement(name = "predictionsList")
public class PredictionsList {
	
	private List<Prediction> predictionsList;
	private AtomicInteger predId;
	
	public PredictionsList() {
		
		this.predictionsList = new CopyOnWriteArrayList<Prediction>();
		this.predId = new AtomicInteger();
	}
	
	@XmlElement
	@XmlElementWrapper(name ="predictions")
	public List<Prediction> getPredictions() {
		return this.predictionsList;
	}
	
	public void setPredictions(List<Prediction> predictionsList) {
		this.predictionsList = predictionsList;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(Prediction prediction: predictionsList) 
			s += prediction.toString();		
		return s;
	}
	
	public Prediction find(int id) {
		
		Prediction prediction = null;
		
		for(Prediction p: predictionsList) {
			if(p.getId() == id) {
				prediction = p;
				break;
			}
		}
		
		return prediction;
	}
	
	public int add(String who, String what) {
		
		int id = predId.incrementAndGet();
		
		Prediction p = new Prediction();
		p.setId(id);
		p.setWho(who);
		p.setWhat(what);
		
		predictionsList.add(p);
		
		return id;
	}
}
