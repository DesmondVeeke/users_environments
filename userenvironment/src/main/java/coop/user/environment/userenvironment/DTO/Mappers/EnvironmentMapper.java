package coop.user.environment.userenvironment.DTO.Mappers;

import coop.user.environment.userenvironment.DTO.Environment.EnvironmentDTO;
import coop.user.environment.userenvironment.Entities.Environment;
import coop.user.environment.userenvironment.Entities.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EnvironmentMapper {
    public EnvironmentDTO EnvironmentToDTO(Environment environment) {
        EnvironmentDTO dto = new EnvironmentDTO();
        dto.setOwner_id(environment.getOwner().getId());
        dto.setId(environment.getId());
        dto.setName(environment.getName());
        dto.setSongs(environment.getSongs());
        dto.setParticipants(environment.getParticipants().stream()
                .map(User::getId)
                .collect(Collectors.toList()));
        dto.setOwner(environment.getOwner().getId() == dto.getOwner_id());
        return dto;
    }
}
