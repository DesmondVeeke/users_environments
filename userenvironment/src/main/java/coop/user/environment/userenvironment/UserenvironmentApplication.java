package coop.user.environment.userenvironment;

import coop.user.environment.userenvironment.Entities.Environment;
import coop.user.environment.userenvironment.Entities.User;
import coop.user.environment.userenvironment.Interfaces.EnvironmentRepository;
import coop.user.environment.userenvironment.Interfaces.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UserenvironmentApplication {


	public static void main(String[] args) {
		SpringApplication.run(UserenvironmentApplication.class, args);
	}

	@Bean
	public CommandLineRunner addUsers(UserRepository userRepository, EnvironmentRepository environmentRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {
			User user1 = new User();
			user1.setEmail("desmond@fontys.nl");
			user1.setPassword(passwordEncoder.encode("rootpw"));
			user1.setAdmin(true);

			User user2 = new User();
			user2.setEmail("user2@example.com");
			user2.setPassword(passwordEncoder.encode("password2"));

			User user3 = new User();
			user3.setEmail("user3@example.com");
			user3.setPassword(passwordEncoder.encode("password3"));

			User user4 = new User();
			user4.setEmail("user4@example.com");
			user4.setPassword(passwordEncoder.encode("password4"));

			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
			userRepository.save(user4);


			// Create environments with users as owners
			Environment environment1 = new Environment();
			environment1.setName("Desmond's music project");
			environment1.setSongs(Arrays.asList(1L, 2L, 3L));
			environment1.setOwner(userRepository.findById(user1.getId()).orElseThrow());
			environment1.setParticipants(new ArrayList<>(Arrays.asList(
					userRepository.findById(user2.getId()).orElseThrow(),
					userRepository.findById(user3.getId()).orElseThrow()
			)));

			Environment environment2 = new Environment();
			environment2.setName("Pushing 40 & Smoke Ranger");
			environment2.setOwner(userRepository.findById(user2.getId()).orElseThrow());
			environment2.setParticipants(new ArrayList<>());

			Environment environment3 = new Environment();
			environment3.setName("DJ EuroShopfer");
			environment3.setOwner(userRepository.findById(user3.getId()).orElseThrow());
			environment3.setParticipants(new ArrayList<>());

			Environment environment4 = new Environment();
			environment4.setName("Milan's supercoole Ableton escapade");
			environment4.setSongs(Collections.emptyList()); // If no songs, set an empty list
			environment4.setOwner(userRepository.findById(user4.getId()).orElseThrow());
			environment4.setParticipants(new ArrayList<>(Arrays.asList(
					userRepository.findById(user2.getId()).orElseThrow(),
					userRepository.findById(user3.getId()).orElseThrow()
			)));

			environmentRepository.save(environment1);
			environmentRepository.save(environment2);
			environmentRepository.save(environment3);
			environmentRepository.save(environment4);
		};
	}


}
