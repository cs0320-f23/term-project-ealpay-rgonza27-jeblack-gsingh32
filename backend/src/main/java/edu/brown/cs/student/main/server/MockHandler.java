package edu.brown.cs.student.main.server;

import spark.Request;
import spark.Response;
import spark.Route;

/** MockHandler to mimic BroadBandHandler functionality in testing. */
public class MockHandler implements Route {

  private String fakeArray;

  /**
   * Constructor for MockHAndler used to test functionality of broadBand Handler without actual
   * requests to the API.
   *
   * @param fakeArray fake datatype
   */
  public MockHandler(String fakeArray) {
    this.fakeArray = fakeArray;
  }

  /**
   * Handle method that overrides it from the Route interface. Handles the same things as the
   * broadband handler but on fake data not retrieved from any real API.
   *
   * @param request request mock
   * @param response response mock
   * @return Status update (success/failure)
   * @throws Exception any exception handled
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    try {
      String state = request.queryParams("State");
      String county = request.queryParams("county");
      response.type("application/json");
      if ((state == null || county == null)) {
        throw new IllegalArgumentException(
            "Please Enter a valid Query: broadBand?State=[State name]&county=[County name]");
      }
      if ((state.isEmpty() || county.isEmpty())) {
        throw new IllegalArgumentException("Please Enter a State and County");
      }

      return ApiUtilities.toJson(ApiUtilities.fromJson(fakeArray));
    } catch (Exception a) {
      return ApiUtilities.toJsonStatus(a.getMessage(), true);
    }
  }
}
