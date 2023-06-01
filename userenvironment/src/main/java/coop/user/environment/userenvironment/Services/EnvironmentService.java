package coop.user.environment.userenvironment.Services;

import coop.user.environment.userenvironment.DTO.Environment.EnvironmentDTO;
import coop.user.environment.userenvironment.Entities.Environment;
import coop.user.environment.userenvironment.Entities.User;
import coop.user.environment.userenvironment.Interfaces.EnvironmentRepository;
import coop.user.environment.userenvironment.Interfaces.EnvironmentRepositoryCustom;
import coop.user.environment.userenvironment.Interfaces.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnvironmentService {
    private final EnvironmentRepositoryCustom environmentRepository;
    private final UserRepositoryCustom userRepository;

    @Autowired
    public EnvironmentService(EnvironmentRepositoryCustom environmentRepository, UserRepositoryCustom userRepository) {
        this.environmentRepository = environmentRepository;
        this.userRepository = userRepository;
    }

    public Boolean createEnvironment(EnvironmentDTO dto) {
        //Find the user that created the environment
        Optional<User> ownerOptional = userRepository.findById(dto.getOwner_id());
        if(ownerOptional.isEmpty()){
            return false;
        }

        User owner = ownerOptional.get();

        Environment environment = new Environment();
        environment.setOwner(owner);
        environment.setName(dto.getName());

        environmentRepository.save(environment);

        return true;
    }
}
