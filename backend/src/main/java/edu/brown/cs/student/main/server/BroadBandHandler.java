package edu.brown.cs.student.main.server;

import spark.Request;
import spark.Response;
import spark.Route;

/** Endpoint class for BroadBand that calls the US API. */
public class BroadBandHandler implements Route {

  private CacheData cacheData;

  /**
   * Constructor for the BroadBand Handler, takes in an instance of cacheData to utilize its caching
   * functionality.
   */
  public BroadBandHandler(CacheData cacheData) {
    this.cacheData = cacheData;
  }

  /**
   * Handle method that overrides it from the Route interface. Handles the /broadBand endpoint that
   * takes in two query parameters in the form of a State and County. Uses an instance of the cache
   * class (instance variable) and the APIUtilities class to get broadBand info for the given state,
   * county pair, storing the data in the cache in the process.
   *
   * @param request the request to handle
   * @param response use to modify properties of the response
   * @return response content for broadband information
   * @throws Exception catches any exception
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
      return ApiUtilities.toJson(ApiUtilities.fromJson(cacheData.output(state + "," + county)));
    } catch (Exception a) {
      System.out.println(a);
      return ApiUtilities.toJsonStatus(a.getMessage(), true);
    }
  }
}
