package org.clp.xslt.createincident.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.clp.xslt.createincident.mq.oop.OOPMQ;
import org.clp.xslt.ws.pojo.CreatedIncidents;
import org.clp.xslt.ws.pojo.RequestMessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class XsltTransformer {

	private static Logger logger = LoggerFactory.getLogger(XsltTransformer.class);
	public static void XsltMapperRequest(CreatedIncidents input) throws JAXBException, TransformerException, IOException, DatatypeConfigurationException, SAXException, ParseException
	{
		 	
		logger.info("Request received for Create Incident with input ");

        JAXBContext context = JAXBContext.newInstance(RequestMessageType.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        input.getHeader().setSource("Adapter");
        StringWriter sw = new StringWriter();
        marshaller.marshal(input, sw);
        logger.info(sw.toString());
		logger.info("");
		
		
        // XSL transform 
        logger.info("Transforming Message using XSLT");
        
        //Need to Update XSLT
		/*
		 * TransformerFactory tf = TransformerFactory.newInstance(); StreamSource xslt =
		 * new StreamSource("classpath:/xslt/CreateIncident.xsl"); Transformer
		 * transformer = tf.newTransformer(xslt); JAXBContext jc =
		 * JAXBContext.newInstance(CreatedIncidents.class); JAXBSource source = new
		 * JAXBSource(jc, input);
		 * 
		 * StringWriter writer = new StringWriter(); StreamResult result = new
		 * StreamResult(writer); transformer.transform(source, result);
		 * 
		 * String outputXML = writer.getBuffer().toString(); writer.close();
		 * logger.info("XSLT Converted Message : "+ outputXML);
		 */
        
        logger.info("Sending Message to AQ");
        OOPMQ obj=new OOPMQ();
        obj.convertAndSend(sw.toString());
        logger.info("");
        
       // PtpProducer.convertAndSend();
        logger.info("Sent XSLT Transformed message to AQ");
	       
	}
}
