package coop.user.environment.userenvironment.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import coop.user.environment.userenvironment.DTO.User.LoginDTO;
import coop.user.environment.userenvironment.DTO.User.RegisterDTO;
import coop.user.environment.userenvironment.DTO.User.UserDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@Tag("IntegrationTests")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetUserById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("COOP", "TRUE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("desmond@fontys.nl")));
    }

    @Test
    public void GetUserById_UserNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("COOP", "TRUE"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void CreateUser() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@example.com");
        registerDTO.setPassword("Pr0p3rPassWordddd%");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("COOP", "TRUE")
                        .content(asJsonString(registerDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CreateUser_UserCreationFailed() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@example.com");
        registerDTO.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("COOP", "TRUE")
                        .content(asJsonString(registerDTO)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void LoginUser() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("desmond@fontys.nl");
        loginDTO.setPassword("rootpw");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("COOP", "TRUE")
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
                        .header("COOP", "TRUE")
                        .content(asJsonString(loginDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Invalid credentials"));
    }

    @Test
    public void UpdateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("updated@example.com");
        userDTO.setPassword("Th1s!p4s$w0rdisSafe");


        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("COOP", "TRUE")
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("updated@example.com")));
    }

    @Test
    public void UpdateUser_UserNotFound() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("updated@example.com");
        userDTO.setPassword("1234N3wP!sword");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("COOP", "TRUE")
                        .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void DeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", 4L)
                        .header("COOP", "TRUE"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void DeleteUser_UserNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", 0L)
                        .header("COOP", "TRUE"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

