package coop.user.environment.userenvironment.DTO.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    public long user_id;
    public String email;
    public String password;
}
