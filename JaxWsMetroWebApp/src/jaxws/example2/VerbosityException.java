package jaxws.example2;

public class VerbosityException extends Exception {
	
	private static final long serialVersionUID = 115351025598265960L;
	
	private String details;
	
	public VerbosityException(String reason, String details) {
		
		super(reason);
		this.details = details;
	}
	
	public String getFaultInfo() {
		return this.details;
	}
}
