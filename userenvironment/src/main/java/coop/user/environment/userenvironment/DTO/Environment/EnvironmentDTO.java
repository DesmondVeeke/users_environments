package coop.user.environment.userenvironment.DTO.Environment;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EnvironmentDTO {
    private long owner_id;
    private long id;
    private String name;
    private List<Long> songs;
    private List<Long> participants;

    private Boolean owner = false;
}
