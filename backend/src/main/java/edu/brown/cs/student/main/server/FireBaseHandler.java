package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.csvSearch.Container;
import java.util.List;
import spark.Request;
import spark.Response;
import spark.Route;

/** endpoint handler for viewCSV. */
public class FireBaseHandler implements Route {


  /**
   * Constructor for the ViewHandler, takes in a shared version of a container object. (Shared
   * between LoadCSV, viewCSV, searchCSV)
   */
  public FireBaseHandler() {
  }

  /**
   * Handle method that overrides it from the Route interface. Handles the /viewCSV endpoint that
   * takes in no query parameters . Uses an instance of the container class (instance variable) and
   * the APIUtilities class to display the CSV that has been previously loaded.
   *
   * @param request request made for viewCSV
   * @param response expected response for viewCSV
   * @return JSON Object depending on response
   * @throws Exception catches all exceptions
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    try {
      response.type("application/json");
      return null;
    } catch (NullPointerException e) {
      return ApiUtilities.toJsonStatus(e.getMessage(), true);
    }
  }
}
