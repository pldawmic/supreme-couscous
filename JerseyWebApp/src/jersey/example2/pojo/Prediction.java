package jersey.example2.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "prediction")
public class Prediction implements Comparable<Prediction> {

	private String who;
	private String what;
	private int id;
	
	public Prediction() {}
	
	@XmlElement
	public String getWho() {return who;}
	public void setWho(String who) {this.who = who;}
	
	@XmlElement
	public String getWhat() { return this.what; }
	public void setWhat(String what) { this.what = what; }
	
	@XmlElement
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	
	@Override
	public String toString() {
		return String.format("%2d: ", id) + who + " ==> " + what + "\n";
	}
	
	@Override
	public int compareTo(Prediction other) { return this.id - other.id; }
}
