package coop.user.environment.userenvironment.Controllers;

import coop.user.environment.userenvironment.DTO.Environment.EnvironmentDTO;
import coop.user.environment.userenvironment.Entities.Environment;
import coop.user.environment.userenvironment.Services.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
