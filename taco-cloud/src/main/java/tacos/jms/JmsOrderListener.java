package tacos.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;

@Slf4j
@Component
public class JmsOrderListener {
	@JmsListener(destination = "tacocloud.order.queue")
	public void receiver(Order order) {
		log.info("order: " + order);
	}
}
