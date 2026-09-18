package es.upm.miw.devops.rest.repositories;

import es.upm.miw.devops.rest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}