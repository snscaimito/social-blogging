package net.caimito.socialblogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SocialBloggingApplication {

  public static void main(String[] args) {
    SpringApplication.run(SocialBloggingApplication.class, args);
  }

}
