package coop.user.environment.userenvironment.Services;

import coop.user.environment.userenvironment.DTO.Environment.EnvironmentDTO;
import coop.user.environment.userenvironment.DTO.Mappers.EnvironmentMapper;
import coop.user.environment.userenvironment.Entities.Environment;
import coop.user.environment.userenvironment.Entities.User;
import coop.user.environment.userenvironment.Interfaces.EnvironmentRepositoryCustom;
import coop.user.environment.userenvironment.Interfaces.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnvironmentService {
    private final EnvironmentRepositoryCustom environmentRepository;
    private final UserRepositoryCustom userRepository;
    private final EnvironmentMapper environmentMapper;

    @Autowired
    public EnvironmentService(EnvironmentRepositoryCustom environmentRepository, UserRepositoryCustom userRepository, EnvironmentMapper environmentMapper) {
        this.environmentRepository = environmentRepository;
        this.userRepository = userRepository;
        this.environmentMapper = environmentMapper;
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

    public EnvironmentDTO getEnvironment(long environmentId) {
        Environment environment = environmentRepository.findById(environmentId).orElse(null);

        if (environment == null) {
            return null;
        }

        return environmentMapper.EnvironmentToDTO(environment);
    }

    public List<EnvironmentDTO> getEnvironmentsByParticipantId(long userId) {
        User userOptional = userRepository.findById(userId).orElse(null);

        if (userOptional != null) {
            List<Environment> environments = new ArrayList<>();

            // Find environments where the user is a participant
            List<Environment> participantEnvironments = environmentRepository.findByParticipantsContaining(userOptional);
            environments.addAll(participantEnvironments);

            // Find environments where the user is the owner
            List<Environment> ownerEnvironments = environmentRepository.findByOwner_Id(userId);
            environments.addAll(ownerEnvironments);

            return environments.stream()
                    .map(environmentMapper::EnvironmentToDTO)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public Environment updateEnvironment(EnvironmentDTO environmentDTO) {
        Optional<Environment> environmentOptional = environmentRepository.findById(environmentDTO.getId());

        if (environmentOptional.isPresent()) {
            Environment environment = environmentOptional.get();
            List<Long> participantIds = environmentDTO.getParticipants();
            List<User> participants = userRepository.findAllById(participantIds);

            environment.setParticipants(participants);
            environment.setName(environmentDTO.getName());
            environment.setSongs(environmentDTO.getSongs());

            environmentRepository.save(environment);

            return environment;
        } else {
            return null;
        }
    }

    public boolean deleteEnvironment(long environmentId) {
        Optional<Environment> environmentOptional = environmentRepository.findById(environmentId);

        if (environmentOptional.isPresent()) {
            Environment environment = environmentOptional.get();
            environmentRepository.delete(environment);
            return true;
        }

        return false;
    }



}
