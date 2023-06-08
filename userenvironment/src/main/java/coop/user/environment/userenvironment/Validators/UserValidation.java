package coop.user.environment.userenvironment.Validators;

import coop.user.environment.userenvironment.DTO.User.RegisterDTO;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidation {

    public boolean isValidRegistration(RegisterDTO dto) throws Exception {
        try{
            validateEmail(dto.email);
            validatePassword(dto.password);

            return true;
        }
        catch(Exception e){
            throw e;
        }

    }

    public void validateEmail(String email) throws Exception {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (!pattern.matcher(email).matches()) {
            throw new Exception("Invalid email address.");
        }
    }

    public void validatePassword(String password) throws  Exception {
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*[@#$%^&+=_\\-!¡?¿*\\d])(?!.*\\s).{12,}$";
        Pattern pattern = Pattern.compile(passwordRegex);

        if (!pattern.matcher(password).matches()) {
            throw new Exception("Invalid password. Password should contain at least one letter, one special character, no whitespace, and be at least 12 characters long.");
        }
    }

}
