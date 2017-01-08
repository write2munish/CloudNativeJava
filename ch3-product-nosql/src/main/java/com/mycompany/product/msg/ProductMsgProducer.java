package com.mycompany.product.msg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Component;

import com.mycompany.product.entity.Product;

@Component
public class ProductMsgProducer {

	JmsMessagingTemplate prodUpdtemplate ;

	@Value("${jms.ProductTopic}")
	private String productTopic ;

	public ProductMsgProducer(JmsMessagingTemplate template ) {
		
		JmsTemplate srcTemplate = template.getJmsTemplate() ;
		srcTemplate.setPubSubDomain(true);						// topic is true, queue is false 
		srcTemplate.setDeliveryPersistent(true);				// we want our message to live through broker shutdown		
		template.setJmsMessageConverter(jacksonJmsMessageConverter()); // convert 
		
		prodUpdtemplate = template ;		
	}
	
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		return converter;
	}
	
	public void sendUpdate(Product product, boolean isDelete) {
		ProductUpdMsg msg = new ProductUpdMsg(product, isDelete);
		prodUpdtemplate.convertAndSend(productTopic, msg); 
	}	
	
}
