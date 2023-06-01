package coop.user.environment.userenvironment.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import coop.user.environment.userenvironment.DTO.User.LoginDTO;
import coop.user.environment.userenvironment.DTO.User.RegisterDTO;
import coop.user.environment.userenvironment.DTO.User.UserDTO;
import coop.user.environment.userenvironment.Entities.User;
import coop.user.environment.userenvironment.Interfaces.UserRepository;
import coop.user.environment.userenvironment.Interfaces.UserRepositoryCustom;
import coop.user.environment.userenvironment.Services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;

@AutoConfigureMockMvc
@SpringBootTest
@Tag("IntegrationTests")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepositoryCustom userRepository;

    @Test
    public void GetUserById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("user1@example.com")));
    }

    @Test
    public void GetUserById_UserNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", 0L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void CreateUser() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@example.com");
        registerDTO.setPassword("Pr0p3rPassWordddd%");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CreateUser_UserCreationFailed() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@example.com");
        registerDTO.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerDTO)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void LoginUser() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("user1@example.com");
        loginDTO.setPassword("password1");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Logged in successfully"));
    }

    @Test
    public void LoginUser_InvalidCredentials() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Invalid credentials"));
    }

    @Test
    public void UpdateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("updated@example.com");
        userDTO.setPassword("newpassword");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setEmail("updated@example.com");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("updated@example.com")));
    }

    @Test
    public void UpdateUser_UserNotFound() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("updated@example.com");
        userDTO.setPassword("newpassword");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void DeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", 4L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void DeleteUser_UserNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", 0L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

