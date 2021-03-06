
package jaxws.metro.client3;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "VerbosityException", targetNamespace = "http://example2.jaxws/")
public class VerbosityException_Exception
    extends Exception
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1636294932943034772L;
	/**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private VerbosityException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public VerbosityException_Exception(String message, VerbosityException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public VerbosityException_Exception(String message, VerbosityException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: jaxws.metro.client3.VerbosityException
     */
    public VerbosityException getFaultInfo() {
        return faultInfo;
    }

}
