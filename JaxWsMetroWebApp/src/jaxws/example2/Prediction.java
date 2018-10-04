package jaxws.example2;

import java.io.Serializable;

public class Prediction implements Serializable, Comparable<Prediction> {

	private static final long serialVersionUID = 7242892436430609362L;
	
	private String who;
	private String what;
	
	private int id;
	
	public Prediction() {}
	
	@Override
	public int compareTo(Prediction o) {
		return this.id - o.id;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
