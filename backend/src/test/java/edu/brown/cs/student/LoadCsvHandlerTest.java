package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.*;

import edu.brown.cs.student.main.csvSearch.Container;
import edu.brown.cs.student.main.server.LoadCsvHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadCsvHandlerTest {

  private LoadCsvHandler loadCSVHandler;
  private RequestStub mockRequest;
  private ResponseStub mockResponse;
  private static Container<List<String>> sharedContainer = new Container<>();

  @BeforeEach
  void setUp() {
    loadCSVHandler = new LoadCsvHandler(sharedContainer);
    mockRequest = new RequestStub();
    mockResponse = new ResponseStub();
  }

  @Test
  void testHandleWithValidInput() {
    // Set query parameters for a valid input
    mockRequest.queryParamsPut("Path", "RIincome.csv");
    mockRequest.queryParamsPut("hasHeader", "true");

    try {
      Object result = loadCSVHandler.handle(mockRequest, mockResponse);

      assertTrue(sharedContainer.rows.toString().contains("rhode island"));
      assertFalse(sharedContainer.rows.toString().contains("latin"));
      mockRequest.queryParamsPut("Path", "census/dol_ri_earnings_disparity.csv");
      Object result2 = loadCSVHandler.handle(mockRequest, mockResponse);
      assertFalse(sharedContainer.rows.toString().contains("rhode island"));
      assertTrue(sharedContainer.rows.toString().contains("latin"));
      assertNotNull(result);
      assertNotNull(result2);

    } catch (Exception e) {
      fail("Exception should not be thrown for valid input.");
    }
  }

  @Test
  void testHandleWithMissingPathParameter() {
    // Set query parameters with missing Path
    mockRequest.queryParamsPut("hasHeader", "true");

    try {
      Object result = loadCSVHandler.handle(mockRequest, mockResponse);

    } catch (IllegalArgumentException e) {
      assertEquals("error_bad_request: Please put all fields", e.getMessage());
    } catch (Exception e) {
      fail("Unexpected exception type: " + e.getClass().getSimpleName());
    }
  }

  @Test
  void testHandleWithInvalidHasHeaderParameter() {
    // Set query parameters with an invalid hasHeader parameter
    mockRequest.queryParamsPut("Path", "census/dol_ri_earnings_disparity.csv");
    mockRequest.queryParamsPut("hasHeader", "invalid");

    try {
      Object result = loadCSVHandler.handle(mockRequest, mockResponse);

    } catch (IllegalArgumentException e) {
      assertEquals("error_bad_request: Please put all fields", e.getMessage());
    } catch (Exception e) {
      fail("Unexpected exception type: " + e.getClass().getSimpleName());
    }
  }
}
