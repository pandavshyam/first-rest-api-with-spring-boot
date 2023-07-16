package com.example.springboot.firstrestapi.user;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private UserDetailsRepository repository;

  public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
    this.repository = repository;
  }

  @Override
  public void run(String... args) throws Exception {
    logger.info(Arrays.toString(args).toString());
    repository.save(new UserDetails("Shyam", "admin"));
    repository.save(new UserDetails("Prashant", "user"));
    repository.save(new UserDetails("Kanchan", "admin"));
    repository.save(new UserDetails("Sangita", "admin"));

    // List<UserDetails> users = repository.findAll();
    List<UserDetails> users = repository.findByRole("admin");

    users.forEach(user -> logger.info(user.toString()));
  }
}
