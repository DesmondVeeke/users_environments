package coop.user.environment.userenvironment.Controllers;

import coop.user.environment.userenvironment.DTO.Environment.EnvironmentDTO;
import coop.user.environment.userenvironment.Entities.Environment;
import coop.user.environment.userenvironment.Services.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http:localhost:3000")
@RestController
@RequestMapping("/api/environments")

public class EnvironmentController {

    final EnvironmentService environmentService;

    @Autowired
    public EnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    @PostMapping
    public ResponseEntity<EnvironmentDTO> createEnvironment(@RequestBody EnvironmentDTO environmentDTO) {
        Boolean createdEnvironment = environmentService.createEnvironment(environmentDTO);

        if (createdEnvironment) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentDTO> getEnvironment(@PathVariable long id) {
        EnvironmentDTO foundDto = environmentService.getEnvironment(id);

        if (foundDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(foundDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/participant/{userId}")
    public ResponseEntity<List<EnvironmentDTO>> getEnvironmentsByParticipantId(@PathVariable long userId) {
        List<EnvironmentDTO> environments = environmentService.getEnvironmentsByParticipantId(userId);

        if (!environments.isEmpty()) {
            return ResponseEntity.ok(environments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEnvironment(@RequestBody EnvironmentDTO environmentDTO) {
        Environment updated = environmentService.updateEnvironment(environmentDTO);

        if (updated != null) {
            return ResponseEntity.ok("Environment updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnvironment(@PathVariable("id") long environmentId) {
        boolean deleted = environmentService.deleteEnvironment(environmentId);

        if (deleted) {
            return ResponseEntity.ok("Environment deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
