package jaxws.metro.client3.handler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import jaxws.metro.client3.Delete;
import jaxws.metro.client3.Edit;
import jaxws.metro.client3.GetOne;

class IdHandler implements LogicalHandler<LogicalMessageContext> {
	
	public void close(MessageContext mctx) {}

	public boolean handleFault(LogicalMessageContext lmctx) {
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean handleMessage(LogicalMessageContext lmctx) {
		
		Boolean outbound = (Boolean) lmctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		
		if (outbound) { // request?
			
			LogicalMessage msg = lmctx.getMessage();
			try {
				JAXBContext jaxbCtx = JAXBContext.newInstance("clientSOAP");
				Object payload = msg.getPayload(jaxbCtx);
				// Check payload to be sure it's what we want.
				if (payload instanceof JAXBElement) {
					Object value = ((JAXBElement<?>) payload).getValue();
					// Three possibilities of interest: GetOne, Edit, or Delete
					int id = 0;
					boolean getOne, edit, delete;
					getOne = edit = delete = false;
					
					if (value.toString().contains("GetOne")) {
						id = ((GetOne) value).getArg0();
						getOne = true;
					} else if (value.toString().contains("Edit")) {
						id = ((Edit) value).getArg0();
						edit = true;
					} else if (value.toString().contains("Delete")) {
						id = ((Delete) value).getArg0();
						delete = true;
					} else

						return true; // GetAll or Create
					// If id > 0, there is no problem to fix on the client side.
					
					if (id > 0)
						return true;
					
					// If the request is GetOne, Edit, or Delete and the id is zero,
					// there is a problem that cannot be fixed.
					if (getOne || edit || delete) {
						if (id == 0) // can't fix
							throw new RuntimeException("ID cannot be zero!");
						// id < 0 and operation is GetOne, Edit, or Delete
						int newId = Math.abs(id);
						// Update argument.
						if (getOne)
							((GetOne) value).setArg0(newId);
						else if (edit)
							((Edit) value).setArg0(newId);
						else if (delete)
							((Delete) value).setArg0(newId);
						// Update payload.
						((JAXBElement<Object>) payload).setValue(value);
						// Update message
						msg.setPayload(payload, jaxbCtx);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return true;
	}
}
