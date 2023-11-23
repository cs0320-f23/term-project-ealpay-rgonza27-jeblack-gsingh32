package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.csvSearch.Container;
import edu.brown.cs.student.main.csvSearch.Creator;
import edu.brown.cs.student.main.csvSearch.Parse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import spark.Request;
import spark.Response;
import spark.Route;

/** Endpoint class for LoadCSV. */
public class LoadCsvHandler implements Route {

  private final Container<List<String>> shared;

  /**
   * Constructor for LoadCSVHandler takes in a shared version of a container object. (Shared between
   * LoadCSV, viewCSV, searchCSV)
   *
   * @param sharedContainer container object shared between CSV handlers
   */
  public LoadCsvHandler(Container<List<String>> sharedContainer) {
    this.shared = sharedContainer;
  }

  /**
   * Handle method that overrides it from the Route interface. Handles the /loadCSV endpoint that
   * takes in two query parameters in the form of a path and hasHeader. Uses an instance of the
   * Parse class to parse the CSV file provided by the user in path.
   *
   * @param request request query
   * @param response expected response
   * @return JSON object depending on response
   * @throws Exception Catches all exceptions
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    // has to handle if hasHeader isn't given
    try {
      String path = System.getProperty("user.dir") + "/data/" + request.queryParams("Path");
      String hasHeader = request.queryParams("hasHeader");
      if (hasHeader == null) {
        throw new IllegalAccessException();
      }
      boolean headerBool =
          switch (hasHeader) {
            case "true" -> true;
            case "false" -> false;
            default -> throw new IllegalArgumentException("Please provide a boolean");
          };
      Creator row = new Creator();

      response.type("application/json");
      Parse<List<String>> parse = new Parse<>(row, new FileReader(path), headerBool);
      Container<List<String>> container = parse.parseMethod();
      this.shared.rows = container.rows;
      this.shared.hasHeader = container.hasHeader;
      this.shared.header = container.header;
      return ApiUtilities.toJsonStatus(
          "The load and Parse of your File has been successful. "
              + "Please go to /search or /view to visualize it",
          false);
    } catch (FileNotFoundException a) {
      return ApiUtilities.toJsonStatus("error_data_source(filename does not exist)", true);
      // catch missing parameter issue here:
    } catch (IllegalAccessException e) {
      return ApiUtilities.toJsonStatus("error_bad_request: Please put all fields", true);
    } catch (Exception e) {
      return ApiUtilities.toJsonStatus(e.getMessage(), true);
    }
  }
}
