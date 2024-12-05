package tacos.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import tacos.Order;
import tacos.Users;

public interface OrderRepository extends CrudRepository<Order, Long> {
	List<Order> findByUsersOrderByPlacedAtDesc(Users users, Pageable pageable);
}
