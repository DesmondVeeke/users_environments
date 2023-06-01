package coop.user.environment.userenvironment.DTO.Mappers;

import coop.user.environment.userenvironment.DTO.User.RegisterDTO;
import coop.user.environment.userenvironment.DTO.User.UserDTO;
import coop.user.environment.userenvironment.Entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapRegisterToUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setId(registerDTO.user_id);
        user.setEmail(registerDTO.email);
        user.setPassword(registerDTO.password);
        return user;
    }

    public User UserDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        // Map other properties as needed

        return user;
    }
}
