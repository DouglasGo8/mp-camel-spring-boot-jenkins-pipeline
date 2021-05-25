package org.camel.jenkins.pipeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MySpringBootApplication {

  /**
   * A main method to start this application.
   */
  public static void main(String[] args) {
    SpringApplication.run(MySpringBootApplication.class, args);
  }

  @RestController
  static class MyResource {
    @GetMapping
    public String sayHi() {
      return "Hi Jenkins";
    }
  }

}
