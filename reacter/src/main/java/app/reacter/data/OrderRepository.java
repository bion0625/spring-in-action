package app.reacter.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import app.reacter.Order;
import app.reacter.Users;


public interface OrderRepository extends CrudRepository<Order, Long> {
	List<Order> findByUsersOrderByPlacedAtDesc(Users users, Pageable pageable);
}
