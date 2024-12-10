package app.reacter.data;

import org.springframework.data.repository.CrudRepository;

import app.reacter.Users;

public interface UserRepository extends CrudRepository<Users, Long> {
	Users findByUsername(String username);
}
