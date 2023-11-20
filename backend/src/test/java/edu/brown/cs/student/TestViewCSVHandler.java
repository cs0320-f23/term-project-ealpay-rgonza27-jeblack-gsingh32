package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.brown.cs.student.main.csvSearch.Container;
import edu.brown.cs.student.main.server.LoadCsvHandler;
import edu.brown.cs.student.main.server.ViewCsvHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestViewCSVHandler {

  private ViewCsvHandler viewCSVHandler;
  private LoadCsvHandler loadCSVHandler;
  private RequestStub mockRequest;
  private ResponseStub mockResponse;
  private static Container<List<String>> sharedContainer = new Container<>();

  @BeforeEach
  void setUp() {
    sharedContainer = new Container<>();
    loadCSVHandler = new LoadCsvHandler(sharedContainer);
    viewCSVHandler = new ViewCsvHandler(sharedContainer);
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
      Object result1 = viewCSVHandler.handle(mockRequest, mockResponse);
      assertTrue(result1.toString().contains("rhode island"));
      assertFalse(sharedContainer.rows.toString().contains("latin"));
      mockRequest.queryParamsPut("Path", "census/dol_ri_earnings_disparity.csv");
      Object result2 = loadCSVHandler.handle(mockRequest, mockResponse);
      result1 = viewCSVHandler.handle(mockRequest, mockResponse);
      assertFalse(result1.toString().contains("rhode island"));
      assertTrue(result1.toString().contains("latin"));
      assertNotNull(result);
      assertNotNull(result2);

    } catch (Exception e) {
      fail("Exception should not be thrown for valid input.");
    }
  }
}
