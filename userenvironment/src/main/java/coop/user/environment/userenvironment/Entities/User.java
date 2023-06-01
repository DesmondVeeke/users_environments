package coop.user.environment.userenvironment.Entities;

import coop.user.environment.userenvironment.Entities.Environment;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
@Setter
@Getter

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    private String email;
    @OneToMany(mappedBy = "owner")
    private List<Environment> environments;

}
