package coop.user.environment.userenvironment.Services;

import coop.user.environment.userenvironment.DTO.Mappers.UserMapper;
import coop.user.environment.userenvironment.DTO.User.LoginDTO;
import coop.user.environment.userenvironment.DTO.User.RegisterDTO;
import coop.user.environment.userenvironment.DTO.User.UserDTO;
import coop.user.environment.userenvironment.Entities.User;
import coop.user.environment.userenvironment.Interfaces.UserRepositoryCustom;
import coop.user.environment.userenvironment.Validators.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService {
    private final UserRepositoryCustom userRepository;
    private final UserValidation userValidation;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepositoryCustom userRepository, UserValidation userValidation, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidation = userValidation;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean addUser(RegisterDTO dto) throws Exception {
        try{
            userValidation.isValidRegistration(dto);
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));

            User newUser = mapper.mapRegisterToUser(dto);
            userRepository.save(newUser);

            return true;
        }
        catch(Exception e){
            throw e;
        }

    }
    public User loginUser(LoginDTO loginDTO) {
        User foundUser = userRepository.findByEmail(loginDTO.getEmail()).orElse(null);

        if (foundUser != null) {
            if (passwordEncoder.matches(loginDTO.getPassword(), foundUser.getPassword())) {
                return foundUser;
            }
        }

        return null;
    }
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public User updateUser(Long id, UserDTO userDTO) throws Exception {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            try {
                User updatedUser = mapper.UserDTOToUser(userDTO);
                userValidation.validatePassword(userDTO.getPassword());

                if (!existingUser.getEmail().equals(updatedUser.getEmail())) {
                    existingUser.setEmail(updatedUser.getEmail());
                }

                if (!passwordEncoder.matches(updatedUser.getPassword(), existingUser.getPassword())) {
                    var hashedPassword = passwordEncoder.encode(updatedUser.getPassword());
                    existingUser.setPassword(hashedPassword);
                }

                return userRepository.save(existingUser);
            } catch (Exception e) {
                throw e;
            }
        }
        throw new Exception("User ID not found");
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
