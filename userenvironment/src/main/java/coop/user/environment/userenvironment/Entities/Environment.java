package coop.user.environment.userenvironment.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Setter
@Getter

public class Environment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private User owner;
    @ManyToMany
    private List<User> participants;

    @ElementCollection
    private List<Long> song_ids;

    private String name;

}
