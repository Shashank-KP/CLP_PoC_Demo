package org.clp.xslt.createincident.soap.endpintimplementation;

import org.clp.xslt.createincident.controller.XsltTransformer;
import org.clp.xslt.createincident.mq.oop.OOPMQ;
import org.clp.xslt.ws.pojo.CreatedIncidents;
import org.clp.xslt.ws.pojo.IncidentsResponseMessage;
import org.clp.xslt.ws.pojo.ObjectFactory;
import org.clp.xslt.ws.pojo.RequestMessageType;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.xml.sax.SAXException;

@Endpoint
public class ADMSCreateIncidentImplementation {

	private Logger logger = LoggerFactory.getLogger(getClass());

    @ResponsePayload
    @PayloadRoot(namespace = "http://iec.ch/TC57/2011/IncidentsMessage", localPart = "CreatedIncidents")
    public IncidentsResponseMessage create(@RequestPayload CreatedIncidents input) throws JAXBException, TransformerException, IOException, DatatypeConfigurationException, SAXException, ParseException{
    	
    	XsltTransformer.XsltMapperRequest(input);
        
       
        ObjectFactory objectFactory = new ObjectFactory();
        IncidentsResponseMessage output = objectFactory.createIncidentsResponseMessage();

        return output;
    }
}
