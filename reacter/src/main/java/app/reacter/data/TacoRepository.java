package app.reacter.data;

import org.springframework.data.jpa.repository.JpaRepository;

import app.reacter.Taco;

public interface TacoRepository extends JpaRepository<Taco, Long> {
}
