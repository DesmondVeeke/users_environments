package coop.user.environment.userenvironment.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Setter
@Getter

public class Environment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "environment_participants",
            joinColumns = @JoinColumn(name = "environment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "environment_songs",
            joinColumns = @JoinColumn(name = "environment_id")
    )
    @Column(name = "song_id")
    private List<Long> songs = new ArrayList<>();

    private String name;

}
