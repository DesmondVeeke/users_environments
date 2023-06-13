package coop.user.environment.userenvironment.Controllers;

import coop.user.environment.userenvironment.DTO.User.LoginDTO;
import coop.user.environment.userenvironment.DTO.User.UserDTO;
import coop.user.environment.userenvironment.DTO.User.RegisterDTO;
import coop.user.environment.userenvironment.Entities.User;
import coop.user.environment.userenvironment.Services.JwtService;
import coop.user.environment.userenvironment.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterDTO userDTO) {
        try{
            userService.addUser(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }
        catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
        User loggedInUser = userService.loginUser(loginDTO);

        if (loggedInUser != null) {

            String token = jwtService.generateJwtToken(loggedInUser);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            headers.add("Access-Control-Expose-Headers", "Authorization");

            return new ResponseEntity<>("Logged in successfully", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody UserDTO userDTO) {
        try{
            User updatedUser = userService.updateUser(id, userDTO);
            if (updatedUser != null) {
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
