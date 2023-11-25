package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.*;

import edu.brown.cs.student.main.csvSearch.Container;
import edu.brown.cs.student.main.server.LoadCsvHandler;
import edu.brown.cs.student.main.server.SearchHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SearchHandlerTest {

  private SearchHandler searchHandler;
  private LoadCsvHandler loadCSVHandler;
  private RequestStub mockRequest;
  private ResponseStub mockResponse;
  private RequestStub mockRequestLoad;
  private ResponseStub mockResponseLoad;
  private static Container<List<String>> sharedContainer = new Container<>();

  @BeforeEach
  void setUp() {
    sharedContainer = new Container<>();
    loadCSVHandler = new LoadCsvHandler(sharedContainer);
    searchHandler = new SearchHandler(sharedContainer);
    mockRequest = new RequestStub();
    mockResponse = new ResponseStub();
    mockRequestLoad = new RequestStub();
    mockResponseLoad = new ResponseStub();
  }

  @Test
  void testHandleWithValidInput() throws Exception {

    mockRequestLoad.queryParamsPut("Path", "RIincome.csv");
    mockRequestLoad.queryParamsPut("hasHeader", "true");
    Object result = loadCSVHandler.handle(mockRequestLoad, mockResponseLoad);

    // Load some data into the shared container

    // Set query parameters for a valid search
    mockRequest.queryParamsPut("column", "");
    mockRequest.queryParamsPut("target", "rhode island");

    try {
      Object result1 = searchHandler.handle(mockRequest, mockResponse);

      assertTrue(result1.toString().contains("rhode island"));

      mockRequest.queryParamsPut("column", "3");
      result1 = searchHandler.handle(mockRequest, mockResponse);

      assertFalse(result1.toString().contains("rhode island"));

      mockRequest.queryParamsPut("column", "0");
      result1 = searchHandler.handle(mockRequest, mockResponse);

      assertTrue(result1.toString().contains("rhode island"));

      mockRequest.queryParamsPut("column", "City/Town");
      result1 = searchHandler.handle(mockRequest, mockResponse);

      assertTrue(result1.toString().contains("rhode island"));

    } catch (Exception e) {
      fail("Exception should not be thrown for valid input.");
    }
  }

  @Test
  void testHandleWithMissingParameters() throws Exception {
    // Set query parameters with missing parameters

    Object result1 = loadCSVHandler.handle(mockRequestLoad, mockResponseLoad);
    mockRequest.queryParamsPut("column", "asdaf");

    try {
      Object result = searchHandler.handle(mockRequest, mockResponse);
    } catch (IllegalArgumentException e) {
      // Ensure that the expected exception is thrown
      assertEquals(
          "error_bad_request: Please use search?column=[column name or number]&target=[word to search]",
          e.getMessage());
    } catch (Exception e) {
      fail("Unexpected exception type: " + e.getClass().getSimpleName());
    }
  }

  @Test
  void testHandleWithNoParsedData() {
    // Set query parameters with missing parameters

    mockRequest.queryParamsPut("column", "asdaf");

    try {
      Object result = searchHandler.handle(mockRequest, mockResponse);
    } catch (IllegalArgumentException e) {
      // Ensure that the expected exception is thrown
      assertEquals(
          "error_bad_request: Please use search?column=[column name or number]&target=[word to search]",
          e.getMessage());
    } catch (Exception e) {
      fail("Unexpected exception type: " + e.getClass().getSimpleName());
    }
  }

  // Add more test cases for other scenarios as needed
}
