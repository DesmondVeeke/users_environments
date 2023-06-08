package coop.user.environment.userenvironment.UnitTests;

import coop.user.environment.userenvironment.Entities.User;
import coop.user.environment.userenvironment.DTO.User.LoginDTO;
import coop.user.environment.userenvironment.DTO.User.RegisterDTO;
import coop.user.environment.userenvironment.DTO.User.UserDTO;
import coop.user.environment.userenvironment.Interfaces.UserRepositoryCustom;
import coop.user.environment.userenvironment.Services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepositoryCustom userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void AddUser_ValidRegistration_ReturnsTrue() throws Exception {
        // Arrange
        String password = "P4ssw!rd1234";
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@test.test");
        registerDTO.setPassword(password);
        boolean result;

        // Act
        try{
            result = userService.addUser(registerDTO);
        }
        catch (Exception e){
            throw e;
        }

        var newUser = userRepository.findByEmail("test@test.test");

        // Assert
        assertTrue(result);
        assertTrue(newUser.isPresent());
        assertTrue(passwordEncoder.matches(password, newUser.get().getPassword()));
    }

    @Test
    void LoginUser_ValidLogin_ReturnsTrue(){
        // Arrange
        String email = "ValidLogin@test.test";
        String password = "P4ssw!rd1234";
        String exceptionMessage = "";

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPassword(password);
        registerDTO.setEmail(email);

        try{
            userService.addUser(registerDTO);
        }
        catch(Exception e){
            exceptionMessage = e.getMessage();
        }

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setPassword(password);

        // Act
        User result = userService.loginUser(loginDTO);

        // Assert
        assertNotNull(result);
    }

    @Test
    void LoginUser_ValidLogin_ReturnsFalse() {
        // Arrange
        String email = "InvalidLogin@test.test";
        String password = "";
        String exceptionMessage = "";
        String expectedMessage = "Invalid password. Password should contain at least one letter, one special character, no whitespace, and be at least 12 characters long.";

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPassword(password);
        registerDTO.setEmail(email);

        try {
            userService.addUser(registerDTO);
        } catch (Exception e) {
            exceptionMessage = e.getMessage();
        }

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setPassword(password);

        // Act
        User result = userService.loginUser(loginDTO);

        // Assert
        assertNull(result);
        assertEquals(exceptionMessage, expectedMessage);
    }

    @Test
    void UpdateUser_ValidUser_ReturnsUpdatedUser() throws Exception {
        // Arrange
        String email = "ValidUpdate@test.test";
        String password = "P4ssw!rd1234";

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPassword(password);
        registerDTO.setEmail(email);
        userService.addUser(registerDTO);

        String newPassword = "678971897GHGHhjau";

        UserDTO userDTO = new UserDTO();
        userDTO.setPassword(newPassword);
        userDTO.setEmail(email);

        User existingUser = userRepository.findByEmail("ValidUpdate@test.test").orElse(null);


        // Act
        User updatedUser = userService.updateUser(existingUser.getId(), userDTO);

        // Assert
        assertNotNull(updatedUser);
        assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()));

    }

}
