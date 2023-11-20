package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.csvSearch.Container;
import edu.brown.cs.student.main.csvSearch.Search;
import java.util.ArrayList;
import java.util.List;
import spark.Request;
import spark.Response;
import spark.Route;

/** Endpoint class for searchCSV. */
public class SearchHandler implements Route {

  private Container<List<String>> container;

  /**
   * Constructor for the SearchHandler, takes in a shared version of a container object. (Shared
   * between LoadCSV, viewCSV, searchCSV)
   *
   * @param container container object shared between CSV handlers
   */
  public SearchHandler(Container<List<String>> container) {
    this.container = container;
  }

  /**
   * Handle method that overrides it from the Route interface. Handles the /Search endpoint that
   * takes in two query parameters in the form of a column and target. Uses a searcher object to
   * search a CSV file given that it has been loaded with /loadCSV before. Catches many errors that
   * could arise due to user and returns appropriate messages for all.
   *
   * @param request request for search
   * @param response response expected for search
   * @return JSON object depending on the response
   * @throws Exception catches any exception
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    String column = request.queryParams("column");
    String target = request.queryParams("target");
    try {
      response.type("application/json");
      Search search = new Search();
      if (column == null || target == null) {
        throw new IllegalAccessException();
      }
      if (this.container.rows.isEmpty() && this.container.header.isEmpty()) {
        throw new NullPointerException(
            "CSV Not Loaded, please load a CSV First or provide a CSV with information in it");
      }
      ArrayList<List<String>> finalList = search.searchItems(this.container, target, column);
      Container<List<String>> containerNew = new Container<>();
      containerNew.hasHeader = this.container.hasHeader;
      containerNew.header = this.container.header;
      containerNew.rows = finalList;
      return ApiUtilities.toJson(containerNew);
    } catch (NoSuchFieldException e) {
      return ApiUtilities.toJsonStatus(
          "error_bad_request: "
              + column
              + " was not found in the file."
              + "The available headers are: "
              + this.container.header,
          true);
    } catch (IllegalAccessException e) {
      return ApiUtilities.toJsonStatus(
          "error_bad_request: Please use search?column="
              + "[column name or number]&target=[word to search]",
          true);
    } catch (IndexOutOfBoundsException e) {
      return ApiUtilities.toJsonStatus(
          "error_bad_request: index provided can't be"
              + "reached. Here are the available columns: "
              + this.container.header
              + " (index 0 -> "
              + "first column)",
          true);
    } catch (Exception e) {
      return ApiUtilities.toJsonStatus(e.getMessage(), true);
    }
  }
}
