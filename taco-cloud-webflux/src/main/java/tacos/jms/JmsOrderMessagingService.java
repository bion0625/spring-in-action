package tacos.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import jakarta.jms.Destination;
import jakarta.jms.Message;
import tacos.Order;

@Service
public class JmsOrderMessagingService {
	private JmsTemplate jms;
	private Destination orderQueue;

	public JmsOrderMessagingService(JmsTemplate jms, Destination orderQueue) {
		this.jms = jms;
		this.orderQueue = orderQueue;
	}

	public void sendOrder(Order order) {
//		jms.send(session -> session.createObjectMessage(order));
//		jms.send(orderQueue, session -> session.createObjectMessage(order));
//		jms.convertAndSend("tacocloud.order.queue", order);
//
//		jms.send(session -> {
//			Message message = session.createObjectMessage(order);
//			message.setStringProperty("X_ORDER_SOURCE", "WEB");
//			return message;
//		});

		jms.convertAndSend("tacocloud.order.queue", order, 
				message -> {
					message.setStringProperty("X_ORDER_SOURCE", "WEB");
					return message;
					});
	}
}
