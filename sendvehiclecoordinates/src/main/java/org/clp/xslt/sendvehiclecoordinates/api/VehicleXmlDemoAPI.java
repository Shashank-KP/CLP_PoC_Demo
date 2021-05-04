package org.clp.xslt.sendvehiclecoordinates.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.TransformerException;

import org.clp.xslt.sendvehiclecoordinates.controller.XsltTransformer;
import org.clp.xslt.sendvehiclecoordinates.model.RestRequestVehiclesCoordinates;
import org.clp.xslt.ws.pojo.RequestMessageType;
import org.clp.xslt.ws.pojo.VehiclesCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class VehicleXmlDemoAPI {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@PostMapping
    public String sendCordinates(@Validated @RequestBody RestRequestVehiclesCoordinates incomingRequest) throws JAXBException, TransformerException, IOException, DatatypeConfigurationException, SAXException, ParseException {
		
		logger.info("-----------------------------------------------------------------------------------");
		logger.info("Request received for Vehicle coordinates with input ");
		JAXBContext context = JAXBContext.newInstance(RestRequestVehiclesCoordinates.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter sw = new StringWriter();
        marshaller.marshal(incomingRequest, sw);
        logger.info(sw.toString());
        
        logger.info("");
		
		logger.info("-----------------------------------------------------------------------------------");
		logger.info("-----------------------------XSLT Transformation-----------------------------------");
		logger.info("-----------------------------------------------------------------------------------");
		XsltTransformer.getXsltTransformedSoapRequest(incomingRequest);
		
		return "Success";
        
    }

}