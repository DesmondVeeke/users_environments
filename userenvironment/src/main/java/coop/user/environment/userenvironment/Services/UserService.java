package coop.user.environment.userenvironment.Services;

import coop.user.environment.userenvironment.DTO.Mappers.UserMapper;
import coop.user.environment.userenvironment.DTO.User.LoginDTO;
import coop.user.environment.userenvironment.DTO.User.RegisterDTO;
import coop.user.environment.userenvironment.DTO.User.UserDTO;
import coop.user.environment.userenvironment.Entities.User;
import coop.user.environment.userenvironment.Interfaces.UserRepository;
import coop.user.environment.userenvironment.Interfaces.UserRepositoryCustom;
import coop.user.environment.userenvironment.Validators.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepositoryCustom userRepository;
    private final UserValidation userValidation;

    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepositoryCustom userRepository, UserValidation userValidation, UserMapper mapper) {
        this.userRepository = userRepository;
        this.userValidation = userValidation;
        this.mapper = mapper;
    }

    public boolean addUser(RegisterDTO dto){
        //Validate the DTO
        if(!userValidation.isValidRegistration(dto)){
            return false;
        }

        User newUser = mapper.mapRegisterToUser(dto);
        userRepository.save(newUser);

        return true;
    }
    public boolean loginUser(LoginDTO loginDTO) {
        Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(loginDTO.getPassword())) {
                return true;
            }
        }

        return false;
    }
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public User updateUser(Long id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            // Map the UserDTO to the existing User
            User updatedUser = mapper.UserDTOToUser(userDTO);

            // Set the ID and other properties
            updatedUser.setId(existingUser.getId());
            // Set other properties as needed

            return userRepository.save(updatedUser);
        }
        return null;
    }
    public boolean deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}
