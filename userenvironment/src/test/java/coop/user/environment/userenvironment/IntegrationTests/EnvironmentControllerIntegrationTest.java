package coop.user.environment.userenvironment.IntegrationTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import coop.user.environment.userenvironment.DTO.Environment.EnvironmentDTO;
import coop.user.environment.userenvironment.Entities.Environment;
import coop.user.environment.userenvironment.Interfaces.EnvironmentRepositoryCustom;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Tag("IntegrationTests")
public class EnvironmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnvironmentRepositoryCustom environmentRepository;

    @Test
    public void CreateEnvironment() throws Exception {
        // Create a new environment DTO
        EnvironmentDTO environmentDTO = new EnvironmentDTO();
        environmentDTO.setName("New Environment");
        environmentDTO.setOwner_id(1L);

        // Perform the POST request to create the environment
        mockMvc.perform(MockMvcRequestBuilders.post("/api/environments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(environmentDTO)))
                .andExpect(status().isCreated());

        // Verify that the environment was created in the database
        List<Environment> environments = environmentRepository.findAll();
        boolean environmentExists = environments.stream()
                .anyMatch(environment -> environment.getName().equals("New Environment"));

        assertTrue(environmentExists);
    }

    @Test
    public void UpdateEnvironmentName() throws Exception {
        // Create an environment DTO with the updated name
        EnvironmentDTO environmentDTO = new EnvironmentDTO();
        environmentDTO.setId(4L);
        environmentDTO.setName("New Name");
        environmentDTO.setOwner_id(4L);

        List<Long> participants = new ArrayList<>();
        participants.add(2L);
        participants.add(3L);

        environmentDTO.setParticipants(participants);

        // Perform the PUT request to update the environment's name
        mockMvc.perform(MockMvcRequestBuilders.put("/api/environments/{id}", 4L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(environmentDTO)))
                .andExpect(status().isOk());

        // Retrieve the updated environment from the database
        Environment updatedEnvironment = environmentRepository.findById(4L).orElse(null);

        // Assert that the environment's name is updated
        assertNotNull(updatedEnvironment);
        assertEquals("New Name", updatedEnvironment.getName());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
