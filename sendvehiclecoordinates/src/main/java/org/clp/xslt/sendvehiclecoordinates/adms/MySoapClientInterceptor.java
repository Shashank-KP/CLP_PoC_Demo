package org.clp.xslt.sendvehiclecoordinates.adms;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.clp.xslt.sendvehiclecoordinates.controller.XsltTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapEnvelope;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapMessage;

public class MySoapClientInterceptor implements ClientInterceptor {

	private static Logger logger = LoggerFactory.getLogger(MySoapClientInterceptor.class);


    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {

    	logger.info("---------------------intercepted a fault---------------------");
        SoapBody soapBody = getSoapBody(messageContext);
        SoapFault soapFault = soapBody.getFault();
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
		try {
			transformer = tf.newTransformer();
			transformer.transform(soapBody.getSource(), result);
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
        logger.info(writer.toString());
        logger.info("--------------------------------------------------------------");
        throw new RuntimeException(String.format("Error occured while invoking SOAP service - %s ", soapFault.getFaultStringOrReason()));
    }


    private SoapBody getSoapBody(MessageContext messageContext) {
        SoapMessage soapMessage = (SoapMessage) messageContext.getResponse();
        SoapEnvelope soapEnvelope = soapMessage.getEnvelope();
        return soapEnvelope.getBody();
    }

	
	@Override
	public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
		// TODO Auto-generated method stub
		return true;
	}
}    