package net.caimito.socialblogging;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class DataTests {

  @Container
  private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

  @BeforeAll
  public static void setUp() {
    mongoDBContainer.start();
    // Additional setup if needed
  }

  @AfterAll
  public static void tearDown() {
    mongoDBContainer.stop();
    // Additional teardown if needed
  }

  @Test
  public void testSomething() {
    // Your test code here
  }

  // Additional test methods if needed

}
