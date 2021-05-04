package org.clp.xslt.sendvehiclecoordinates.adms;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.stream.StreamResult;

import org.clp.xslt.sendvehiclecoordinates.model.RestRequestVehiclesCoordinates;
import org.clp.xslt.ws.pojo.ChangedVehiclesCoordinates;
import org.clp.xslt.ws.pojo.VehiclesCoordinatesResponseMessage;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;


public class ADMSSoapClient extends WebServiceGatewaySupport {

	public void callADMS(ChangedVehiclesCoordinates request) throws JAXBException{      
	
		//getWebServiceTemplate().marshalSendAndReceive("http://localhost:8088/mock",request);
		VehiclesCoordinatesResponseMessage res= (VehiclesCoordinatesResponseMessage) getWebServiceTemplate().marshalSendAndReceive("http://100.24.79.29:8082/ReceiveVehiclesCoordinatesService",request,new SoapActionCallback("http://iec.ch/TC57/2011/VehiclesCoordinates/ChangedVehiclesCoordinates"));

		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("org.clp.xslt.ws.pojo");

        JAXBContext context = JAXBContext.newInstance(VehiclesCoordinatesResponseMessage.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        marshaller.marshal(res, sw);
        logger.info(sw.toString());
           
    }
}