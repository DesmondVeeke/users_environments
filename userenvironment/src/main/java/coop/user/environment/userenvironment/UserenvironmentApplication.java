package coop.user.environment.userenvironment;

import coop.user.environment.userenvironment.Entities.User;
import coop.user.environment.userenvironment.Interfaces.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserenvironmentApplication {

	private static final Logger log = LoggerFactory.getLogger(UserenvironmentApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UserenvironmentApplication.class, args);
	}

	@Bean
	public CommandLineRunner addUsers(UserRepository userRepository) {
		return (args) -> {
			User user1 = new User();
			user1.setEmail("user1@example.com");
			user1.setPassword("password1");

			User user2 = new User();
			user2.setEmail("user2@example.com");
			user2.setPassword("password2");

			User user3 = new User();
			user3.setEmail("user3@example.com");
			user3.setPassword("password3");

			User user4 = new User();
			user4.setEmail("user4@example.com");
			user4.setPassword("password4");


			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
			userRepository.save(user4);
		};
	}


}
