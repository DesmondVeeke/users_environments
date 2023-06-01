package coop.user.environment.userenvironment.Interfaces;

import coop.user.environment.userenvironment.DTO.User.LoginDTO;
import coop.user.environment.userenvironment.Entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryCustom extends UserRepository {

    Optional<User> findByEmail(String email);

}
