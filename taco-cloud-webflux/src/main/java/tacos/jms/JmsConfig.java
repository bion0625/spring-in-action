package tacos.jms;

import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import jakarta.jms.Destination;
import jakarta.persistence.criteria.Order;

@Configuration
public class JmsConfig {
	@Bean
	public Destination orderQueue() {
		return new ActiveMQQueue("tacocloud.order.queue");
	}

	@Bean
	public MappingJackson2MessageConverter messageConverter() {
		MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
		messageConverter.setTypeIdPropertyName("_typeId");

		Map<String, Class<?>> typeIdMappings = new HashMap<>();
		typeIdMappings.put("order", Order.class);
		messageConverter.setTypeIdMappings(typeIdMappings);

		return messageConverter;
	}
}
