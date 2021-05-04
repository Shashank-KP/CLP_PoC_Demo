package org.clp.xslt.createincident.mq.oop;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class OOPMQ {
	private Logger logger = LoggerFactory.getLogger(getClass());

	String QUEUE_NAME="incidents";

    public void convertAndSend(String data){
    	
    	try{
    		
    		logger.info("Attempting Send message to Queue: "+ QUEUE_NAME);
    		
    		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            //connectionFactory.setBrokerURL("ssl://b-ff2daa33-c7ad-4e52-8ff7-c5cc6fb36f24-1.mq.ap-southeast-1.amazonaws.com:61617");
    		connectionFactory.setBrokerURL("tcp://localhost:61616");
            connectionFactory.setUserName("admin");
            connectionFactory.setPassword("q1w2e3r4t5y6");
    		JmsTemplate jmsTemplate = new JmsTemplate();
    		jmsTemplate.setConnectionFactory(connectionFactory);
    		
    		jmsTemplate.convertAndSend(QUEUE_NAME, data);
    	}catch(Exception e){
    		logger.error("Recieved Exception during send Message: ", e);
         }
    }
   
}
