package coop.user.environment.userenvironment;

import coop.user.environment.userenvironment.Entities.Environment;
import coop.user.environment.userenvironment.Entities.User;
import coop.user.environment.userenvironment.Interfaces.EnvironmentRepository;
import coop.user.environment.userenvironment.Interfaces.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class UserenvironmentApplication {

	private static final Logger log = LoggerFactory.getLogger(UserenvironmentApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UserenvironmentApplication.class, args);
	}

	@Bean
	public CommandLineRunner addUsers(UserRepository userRepository, EnvironmentRepository environmentRepository) {
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

			// Create environments with users as owners
			Environment environment1 = new Environment();
			environment1.setName("Environment 1");
			List<Long> songs = new ArrayList<>();
			songs.add(1L);
			songs.add(2L);
			songs.add(3L);

			List<User> participants = new ArrayList<>();
			participants.add(user2);
			participants.add(user3);


			environment1.setSongs(songs);
			environment1.setOwner(user1);
			environment1.setParticipants(participants);

			Environment environment2 = new Environment();
			environment2.setName("Environment 2");
			environment2.setOwner(user2);
			environment2.setParticipants(new ArrayList<User>());


			Environment environment3 = new Environment();
			environment3.setName("Environment 3");
			environment3.setOwner(user3);
			environment3.setParticipants(new ArrayList<User>());


			Environment environment4 = new Environment();
			environment4.setName("Environment 4");
			environment4.setOwner(user4);
			environment4.setParticipants(participants);


			environmentRepository.save(environment1);
			environmentRepository.save(environment2);
			environmentRepository.save(environment3);
			environmentRepository.save(environment4);
		};
	}


}
