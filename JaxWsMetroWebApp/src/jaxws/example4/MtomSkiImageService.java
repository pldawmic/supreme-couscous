package jaxws.example4;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPBinding;

@WebService(wsdlLocation = "../../WEB-INF/mtomWsdl/mtom.wsdl")
@BindingType(value=SOAPBinding.SOAP11HTTP_MTOM_BINDING)
public class MtomSkiImageService {
	
	private Map<String, String> photos;

	@Resource
	private WebServiceContext wsContext;
	
	public MtomSkiImageService() {

		photos = new HashMap<String, String>();
		photos.put("nordic", "/WEB-INF/skiImages/nordic.jpg");
		photos.put("alpine", "/WEB-INF/skiImages/alpine.jpg");
	}
	
	@WebMethod
	public Image getImage(String name) {
		return createImage(name);
	}

	@WebMethod
	public List<Image> getImages() {
		return createImageList();
	}

	private Image createImage(String name) {
		
		String fileName = photos.get(name);
		
		byte[] bytes = getRawBytes(fileName);
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		
		Iterator<?> iterators = ImageIO.getImageReadersByFormatName("jpeg");
		ImageReader iterator = (ImageReader) iterators.next();
		Image image = null;
		
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(in);
			iterator.setInput(iis, true);
			image = iterator.read(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return image;
	}

	private List<Image> createImageList() {
		
		List<Image> list = new ArrayList<Image>();
		for (String key : photos.keySet()) {
			Image image = createImage(key);
			if (image != null)
				list.add(image);
		}
		return list;
	}

	private byte[] getRawBytes(String fileName) {
		
		if (fileName == null)
			fileName = "nordic.jpg";
		
		ServletContext servletCtx = (ServletContext) wsContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
		
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			
			InputStream in = servletCtx.getResourceAsStream(fileName);
			
			byte[] buffer = new byte[2048];
			int n = 0;
			while ((n = in.read(buffer)) != -1)
				out.write(buffer, 0, n); // append to array
			in.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return out.toByteArray();
	}
}
