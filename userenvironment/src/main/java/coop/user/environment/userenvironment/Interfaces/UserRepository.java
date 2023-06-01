package coop.user.environment.userenvironment.Interfaces;

import coop.user.environment.userenvironment.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
