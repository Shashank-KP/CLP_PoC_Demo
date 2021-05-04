package org.clp.xslt.sendvehiclecoordinates.adms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

@Configuration
public class ADMSSoapClientConfig {

	@Bean
    public Jaxb2Marshaller marshaller(){
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("org.clp.xslt.ws.pojo");
        return jaxb2Marshaller;
    }

    @Bean
    public ADMSSoapClient articleClient(Jaxb2Marshaller jaxb2Marshaller) {
    	ADMSSoapClient admsclient = new ADMSSoapClient();
    	admsclient.setDefaultUri("http://100.24.79.29:8082/ReceiveVehiclesCoordinatesService");
    	admsclient.setMarshaller(jaxb2Marshaller);
    	admsclient.setUnmarshaller(jaxb2Marshaller);
    	ClientInterceptor[] clientInterceptors = {new MySoapClientInterceptor()};
    	admsclient.setInterceptors(clientInterceptors);
        return admsclient;
    }
}
