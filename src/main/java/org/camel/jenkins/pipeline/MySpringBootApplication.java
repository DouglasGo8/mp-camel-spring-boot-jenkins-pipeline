package org.camel.jenkins.pipeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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
    @ResponseBody
    public String sayHi() {
      return "<html><head><title>Fu**-off the MS!</title><body><h1>Hi I'm Jenkins your God!!!</h1></body></html>";
    }
  }

}
