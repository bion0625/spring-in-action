package tacos.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import tacos.Order;

@Controller
public class JmsOrderMessagingController {
	private JmsTemplate jms;

	public JmsOrderMessagingController(JmsTemplate jms) {
		this.jms = jms;
	}

	@GetMapping("/convertAndSend/order")
	public String convertAndSendOrder() {
		Order order = builder();
		jms.convertAndSend("tacocloud.order.queue", order, this::addOrderSource);
		return "Convert and sent Order";
	}

	private Message addOrderSource(Message message) throws JMSException {
		message.setStringProperty("X_ORDER_SOURCE", "WEB");
		return message;
	}

	private Order builder() {
		return new Order();
	}

}
