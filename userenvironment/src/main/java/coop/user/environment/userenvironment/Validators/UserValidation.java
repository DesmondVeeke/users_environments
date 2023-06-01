package coop.user.environment.userenvironment.Validators;

import coop.user.environment.userenvironment.DTO.User.RegisterDTO;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidation {

    public boolean isValidRegistration(RegisterDTO dto) {
        boolean isValidEmail = isValidEmail(dto.getEmail());
        boolean isValidPassword = isValidPassword(dto.getPassword());

        return isValidEmail && isValidPassword;
    }

    private boolean isValidEmail(String email) {
        // Use a regular expression pattern to validate the email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);

        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Add your password validation logic here
        // Example: Minimum length of 8 characters, at least one uppercase letter, one lowercase letter, and one digit
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=_\\-!¡?¿*])(?!.*\\s).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);

        return pattern.matcher(password).matches();
    }
}
