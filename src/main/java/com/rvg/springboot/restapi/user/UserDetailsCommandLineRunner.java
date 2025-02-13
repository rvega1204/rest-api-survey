/**
 * This class implements the CommandLineRunner interface to execute code after the Spring Boot application starts.
 * It is marked as a Spring Component to be detected during component scanning.
 * 
 * The UserDetailsCommandLineRunner class is responsible for:
 * - Saving initial user details to the UserDetailsRepository.
 * - Logging all user details after saving them.
 * 
 * Dependencies:
 * - UserDetailsRepository: Autowired to interact with the database for saving and retrieving user details.
 * 
 * Methods:
 * - run(String... args): Overrides the run method from CommandLineRunner to execute the code at application startup.
 * 
 * Usage:
 * This class will automatically run when the Spring Boot application starts and will save predefined user details
 * to the repository and log all users.
 * 
 * Example:
 * When the application starts, the following users will be saved:
 * - UserDetails("user", "Admin")
 * - UserDetails("user1", "Admin")
 * - UserDetails("user2", "User")
 * 
 * The log will display all users in the repository.
 */
package com.rvg.springboot.restapi.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsCommandLineRunner.class);

    @Autowired
    private UserDetailsRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.save(new UserDetails("rvg", "Admin"));
        repository.save(new UserDetails("user1", "Admin"));
        repository.save(new UserDetails("user2", "User"));

        log.info("All users -> {}", repository.findAll());
        log.info("Admin users -> {}", repository.findByRole("Admin"));
    }

}
