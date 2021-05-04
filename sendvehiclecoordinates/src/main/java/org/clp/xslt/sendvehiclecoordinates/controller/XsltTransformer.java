package org.clp.xslt.sendvehiclecoordinates.controller;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.clp.xslt.sendvehiclecoordinates.model.RestRequestVehiclesCoordinates;
import org.clp.xslt.sendvehiclecoordinates.adms.ADMSSoapClient;
import org.clp.xslt.sendvehiclecoordinates.adms.ADMSSoapClientConfig;
import org.clp.xslt.ws.pojo.ChangedVehiclesCoordinates;
import org.clp.xslt.ws.pojo.ObjectFactory;
import org.clp.xslt.ws.pojo.VehiclesCoordinates;
import org.clp.xslt.ws.pojo.VehiclesCoordinatesPayloadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.SAXException;

public class XsltTransformer {
	
	private static Logger logger = LoggerFactory.getLogger(XsltTransformer.class);
	public static void getXsltTransformedSoapRequest(RestRequestVehiclesCoordinates incomingRequest) throws JAXBException, TransformerException, IOException, DatatypeConfigurationException, SAXException, ParseException
	{
		TransformerFactory tf = TransformerFactory.newInstance();
        StreamSource xslt = new StreamSource("classpath:/xslt/VehicalCoordinates.xsl");
        Transformer transformer = tf.newTransformer(xslt);
        JAXBContext jc = JAXBContext.newInstance(RestRequestVehiclesCoordinates.class);
        JAXBSource source = new JAXBSource(jc, incomingRequest);
 
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);      
      
        String outputXML = writer.getBuffer().toString();
        writer.close();
        logger.info("XSLT Converted Message : "+ outputXML);
		
		JAXBContext jaxbContext =JAXBContext.newInstance(ChangedVehiclesCoordinates.class); 
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		ChangedVehiclesCoordinates xsltOutput = (ChangedVehiclesCoordinates) unmarshaller.unmarshal(new StringReader(outputXML));
		 
		ObjectFactory factory = new ObjectFactory(); ChangedVehiclesCoordinates
		soapRequest=new ChangedVehiclesCoordinates();
		soapRequest.setHeader(xsltOutput.getHeader()); soapRequest.setPayload(null);
		VehiclesCoordinatesPayloadType
		p=factory.createVehiclesCoordinatesPayloadType(); VehiclesCoordinates
		vc=factory.createVehiclesCoordinates();
		VehiclesCoordinates.Vehicle.PositionPoint
		pop=factory.createVehiclesCoordinatesVehiclePositionPoint();
		VehiclesCoordinates.Vehicle vh=factory.createVehiclesCoordinatesVehicle();
		pop.setXPosition(incomingRequest.getxPosition());
		pop.setYPosition(incomingRequest.getyPosition());
		vh.setMRID(incomingRequest.getvID());
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		vh.setOdometerReadDateTime(xmlGregCal); 
		vh.setPositionPoint(pop);
		vc.setVehicle(vh); 
		p.setVehiclesCoordinates(vc);
		soapRequest.setPayload(p);
		  
        logger.info("-----------------------------------Done--------------------------------------------");
		logger.info("");
        logger.info("-----------------------------Calling ADMS------------------------------------------");
		logger.info("-----------------------------------------------------------------------------------");
		AnnotationConfigApplicationContext aCaC=new AnnotationConfigApplicationContext(ADMSSoapClientConfig.class);
		ADMSSoapClient client=aCaC.getBean(ADMSSoapClient.class);
		client.callADMS(soapRequest);
		logger.info("-----------------------------------Done--------------------------------------------");
		
	}

}
