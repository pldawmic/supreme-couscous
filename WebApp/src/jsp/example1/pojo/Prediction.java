package jsp.example1.pojo;

import java.io.Serializable;

public class Prediction implements Serializable {
	
	private static final long serialVersionUID = -2286948137872713190L;
	
	private String who;
	private String what;
	
	public Prediction() {}
	
	public void setWho(String who) { this.who = who;}
	public void setWhat(String what) {this.what = what;}
	
	public String getWho() {return who;}
	public String getWhat() {return what;}
}