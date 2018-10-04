package jaxws.example2.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class ServiceHashHandler implements SOAPHandler<SOAPMessageContext> {
	
	// storing user and user's secret key
	private Map<String, String> DataStore;
	
	public ServiceHashHandler() {
		
		DataStore = new HashMap<>();
		DataStore.put("Moe", "MoeMoeMoe");
	}

	@Override
	public void close(MessageContext mCtx) {}

	@Override
	public boolean handleFault(SOAPMessageContext mCtx) {
		return true;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext mCtx) {

		Boolean outbound = (Boolean) mCtx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (!outbound) {

			try {

				SOAPMessage msg = mCtx.getMessage();
				SOAPHeader soapHeader = msg.getSOAPHeader();

				if (soapHeader == null) {
					generateFault(msg, "No header!");
				}

				Node node = soapHeader.getFirstChild(); // credentials
				NodeList nodeList = node.getChildNodes(); // Name, Signature, Timestamp
				if (nodeList.getLength() < 3) {
					generateFault(msg, "Too few header nodes!");
				}

				// Extract the required attributes
				String name = nodeList.item(0).getFirstChild().getNodeValue();
				String signature = nodeList.item(1).getFirstChild().getNodeValue();
				String timestamp = nodeList.item(2).getFirstChild().getNodeValue();

				if (name == null || signature == null || timestamp == null) {
					generateFault(msg, "Missing header key/value pairs!");
				}

				// Generate comparison signature and compare against what's sent.
				String secretKey = DataStore.get(name);

				if (secretKey == null) {
					generateFault(msg, name + " not registered!");
				}

				byte[] secretBytes = getBytes(secretKey);
				String localSignature = getSignature(name, timestamp, secretBytes);

				if (!verify(signature, localSignature)) {
					generateFault(msg, " HMAC signatures do not match.");
				}

			} catch (SOAPException e) {
				e.printStackTrace();
			}
		}

		return true; //
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}
	
	private void generateFault(SOAPMessage msg, String reason) {
		
		try {
			SOAPBody body = msg.getSOAPBody();
			SOAPFault fault = body.addFault();
			fault.setFaultString(reason);
			throw new SOAPFaultException(fault);
			
		} catch (SOAPException e) {
			e.printStackTrace();
		}
	}
	
	private boolean verify(String sig1, String sig2) {
		return Arrays.equals(sig1.getBytes(), sig2.getBytes());
	}
	
	private String getSignature(String name, String timestamp, byte [] secretBytes) {
		
		try {
			System.err.println("Name == " + name);
			System.err.println("Timestamp == " + timestamp);
			
			String toSign = name + timestamp;
			byte [] toSignBytes = getBytes(toSign);
			
			Mac signer = Mac.getInstance("HmacSHA256");
			
			SecretKeySpec keySpec = new SecretKeySpec(secretBytes, "HmacSHA256");
			signer.init(keySpec);
			signer.update(toSignBytes);
			
			byte [] signBytes = signer.doFinal();
			
			String signature = new String(Base64.encode(signBytes));
			return signature;
			
		} catch (Exception e) {
			throw new RuntimeException("NoSuchAlgorithmException throw." , e);
		}
	}
	
	private byte[] getBytes(String str) {
		try {
			return str.getBytes("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
