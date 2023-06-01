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
    private List<Long> song_ids;
    private List<Long> participant_ids;
}
