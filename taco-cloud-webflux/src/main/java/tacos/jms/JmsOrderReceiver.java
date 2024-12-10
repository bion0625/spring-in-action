package tacos.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import tacos.Order;

@Component
public class JmsOrderReceiver {
	private JmsTemplate jms;
//	private MessageConverter converter;

//	public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
	public JmsOrderReceiver(JmsTemplate jms) {
		this.jms = jms;
//		this.converter = converter;
	}

	public Order orderReceiver() throws MessageConversionException, JMSException {
//		Message message = jms.receive("tacocloud.order.queue");
//		return (Order) converter.fromMessage(message);

		return (Order) jms.receiveAndConvert("tacocloud.order.queue");
	}

}
