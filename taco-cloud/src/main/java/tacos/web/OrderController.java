package tacos.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.Users;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	
	private OrderRepository orderRepo;
	
	public OrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	@GetMapping("/current")
	public String orderForm(@AuthenticationPrincipal Users users, @ModelAttribute Order order) {
		if (order.getDeliveryName() == null) order.setDeliveryName(users.getFullname());
		if (order.getDeliveryStreet() == null) order.setDeliveryStreet(users.getStreet());
		if (order.getDeliveryCity() == null) order.setDeliveryCity(users.getCity());
		if (order.getDeliveryState() == null) order.setDeliveryState(users.getState());
		if (order.getDeliveryZip() == null) order.setDeliveryZip(users.getZip());

		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus,
			@AuthenticationPrincipal Users users) {
		if (errors.hasErrors()) {
			return "orderForm";
		}
		
		order.setUsers(users);
		
		orderRepo.save(order);
		sessionStatus.setComplete();

		return "redirect:/";
	}
}
