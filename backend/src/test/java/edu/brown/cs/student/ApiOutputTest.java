package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.*;

import edu.brown.cs.student.main.server.ApiOutput;
import edu.brown.cs.student.main.server.ApiUtilities;
import edu.brown.cs.student.main.server.Icache;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiOutputTest {

  private Icache apiOutput;

  @BeforeEach
  void setUp() {
    apiOutput = new ApiOutput();
  }

  @Test
  void testOutputWithValidStateAndCounty() throws IOException, ExecutionException {
    String locationInfo = "California,San Francisco County";
    String result = apiOutput.output(locationInfo);
    assertNotNull(result);
    assertTrue(result.contains("87.1"));
    assertTrue(result.contains("075"));
    assertFalse(result.contains("97.1"));
    assertFalse(result.contains("085"));
  }

  @Test
  void testOutputWithInvalidState() {
    // Test the output method with an invalid State
    String locationInfo = "InvalidState,SanF rancisco"; // Replace with an invalid State
    assertThrows(NullPointerException.class, () -> apiOutput.output(locationInfo));
    assertThrows(NullPointerException.class, () -> apiOutput.output(null));
  }

  @Test
  void testOutputWithInvalidCounty() {
    // Test the output method with an invalid County
    String locationInfo = "California,Asdaf"; // Replace with an invalid County
    assertThrows(NullPointerException.class, () -> apiOutput.output(locationInfo));
  }

  @Test
  void testGetStates() throws IOException {
    // Test the getStates method
    String states = apiOutput.getStates();
    assertNotNull(states);
    assertTrue(states.contains("California"));
    assertTrue(states.contains("New York"));
    assertFalse(states.contains("Asdhja"));
  }

  @Test
  void testGetCountiesWithValidState() throws IOException {
    // Test the getCounties method with a valid State
    String state = "California";
    String counties =
        apiOutput.getCounties(ApiUtilities.fromJsonMap(apiOutput.getStates(), true).get(state));
    assertNotNull(counties);
    assertTrue(counties.contains("San Francisco"));

    String state1 = "New York";
    String counties1 =
        apiOutput.getCounties(ApiUtilities.fromJsonMap(apiOutput.getStates(), true).get(state1));
    assertNotNull(counties1);
    System.out.println(counties1);
    assertTrue(counties1.contains("Bronx"));
  }

  @Test
  void testGetCountiesWithEmptyState() {
    // Test the getCounties method with an empty State and invalid state
    String emptyState = "";
    assertThrows(IllegalArgumentException.class, () -> apiOutput.getCounties(emptyState));

    String emptyState1 = "asdasd";
    assertThrows(NullPointerException.class, () -> apiOutput.getCounties(emptyState1));
  }
}
