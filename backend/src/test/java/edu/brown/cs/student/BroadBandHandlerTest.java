package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.*;

import edu.brown.cs.student.main.server.ApiOutput;
import edu.brown.cs.student.main.server.BroadBandHandler;
import edu.brown.cs.student.main.server.CacheData;
import edu.brown.cs.student.main.server.MockHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

public class BroadBandHandlerTest {

  private MockHandler mockHandler;
  private BroadBandHandler broadBandHandler;
  private RequestStub mockRequest;
  private ResponseStub mockResponse;

  @BeforeEach
  void setUp() {
    mockHandler =
        new MockHandler(
            "[[\"NAME\",\"S2802_C03_022E\",\"state\",\"county\"],[\"San Francisco County, California\",\"87.1\",\"06\",\"075\"]]");
    broadBandHandler = new BroadBandHandler(new CacheData(new ApiOutput(), 10, 10));
    mockRequest = new RequestStub();
    mockResponse = new ResponseStub();
  }

  @Test
  void testMockWithValidInput() {
    // Set query parameters for a valid input
    ArrayList<String> list1 = new ArrayList<>();
    mockRequest.queryParamsPut("State", "California");
    mockRequest.queryParamsPut("county", "San Francisco");

    try {
      Object result = mockHandler.handle(mockRequest, mockResponse);

      assertTrue(result.toString().contains("San Francisco"));
      assertTrue(result.toString().contains("87.1"));
      assertFalse(result.toString().contains("99.2"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail("Exception should not be thrown for valid input.");
    }
  }

  @Test
  void testMockWithMissingQueryParameters() {
    // Set query parameters with missing State
    mockRequest.queryParamsPut("county", "");

    try {
      Object result = mockHandler.handle(mockRequest, mockResponse);
    } catch (IllegalArgumentException e) {
      // Ensure that the expected exception is thrown
      assertEquals(
          "Please Enter a valid Query: broadBand?State=[State name]&county=[County name]",
          e.getMessage());
    } catch (Exception e) {
      fail("Unexpected exception type: " + e.getClass().getSimpleName());
    }
  }

  @Test
  void testMockWithEmptyQueryParameters() {
    // Set query parameters with empty State
    mockRequest.queryParamsPut("State", "");
    mockRequest.queryParamsPut("county", "San Francisco");

    try {
      Object result = mockHandler.handle(mockRequest, mockResponse);

    } catch (IllegalArgumentException e) {
      // Ensure that the expected exception is thrown
      assertEquals("Please Enter a State and County", e.getMessage());
    } catch (Exception e) {
      fail("Unexpected exception type: " + e.getClass().getSimpleName());
    }
  }

  @Test
  void testHandleWithValidInput() {
    // Set query parameters for a valid input
    ArrayList<String> list1 = new ArrayList<>();
    mockRequest.queryParamsPut("State", "California");
    mockRequest.queryParamsPut("county", "San Francisco County");

    try {
      Object result = broadBandHandler.handle(mockRequest, mockResponse);

      assertTrue(result.toString().contains("San Francisco"));
      assertTrue(result.toString().contains("87.1"));
      assertFalse(result.toString().contains("99.2"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail("Exception should not be thrown for valid input.");
    }
  }

  @Test
  void testHandleWithMissingQueryParameters() {
    // Set query parameters with missing State
    mockRequest.queryParamsPut("county", "");

    try {
      Object result = broadBandHandler.handle(mockRequest, mockResponse);
    } catch (IllegalArgumentException e) {
      // Ensure that the expected exception is thrown
      assertEquals(
          "Please Enter a valid Query: broadBand?State=[State name]&county=[County name]",
          e.getMessage());
    } catch (Exception e) {
      fail("Unexpected exception type: " + e.getClass().getSimpleName());
    }
  }

  @Test
  void testHandleWithEmptyQueryParameters() {
    // Set query parameters with empty State
    mockRequest.queryParamsPut("State", "");
    mockRequest.queryParamsPut("county", "San Francisco");

    try {
      Object result = broadBandHandler.handle(mockRequest, mockResponse);

    } catch (IllegalArgumentException e) {
      // Ensure that the expected exception is thrown
      assertEquals("Please Enter a State and County", e.getMessage());
    } catch (Exception e) {
      fail("Unexpected exception type: " + e.getClass().getSimpleName());
    }
  }
  // Add more test cases for other scenarios as needed
}

// Helper classes for testing
class RequestStub extends Request {

  public Map<String, String> queryParams = new HashMap<>();

  @Override
  public String queryParams(String queryParam) {
    return queryParams.get(queryParam);
  }

  public void queryParamsPut(String key, String value) {
    this.queryParams.put(key, value);
  }
}

class ResponseStub extends Response {

  public String type;

  @Override
  public void type(String contentType) {
    this.type = contentType;
  }
}
