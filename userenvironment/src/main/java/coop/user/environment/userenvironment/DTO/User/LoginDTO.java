package coop.user.environment.userenvironment.DTO.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String password;
    private String email;
}
