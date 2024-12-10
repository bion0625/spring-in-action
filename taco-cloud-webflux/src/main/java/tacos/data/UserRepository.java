package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.Users;

public interface UserRepository extends CrudRepository<Users, Long> {
	Users findByUsername(String username);
}
