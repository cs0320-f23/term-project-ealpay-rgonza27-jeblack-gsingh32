package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import edu.brown.cs.student.main.Authorization.FirebaseInitialize;
import edu.brown.cs.student.main.server.handlers.*;
import edu.brown.cs.student.main.utils.ImageCacheService;
import org.checkerframework.checker.units.qual.C;
import spark.Spark;

/**
 * Top Level class for our project, utilizes spark to create and maintain our server. We setup all
 * the endpoint handler in the main method for this class. We have four total endpoints: loadCSV,
 * searchCSV, viewCSV, broadBand (and mock but that is not necessary for actual functionality).
 */
public class Server {

  // private static final Container<List<String>> sharedContainer = new Container<>();

  /**
   * Runs Server.
   *
   * @param args none
   */
  public static void main(String[] args) {

    String workingDirectory = System.getProperty("user.dir");
    ImageCacheService imageCacheService = new ImageCacheService();

    // Print the working directory
    System.out.println("Working Directory = " + workingDirectory);
    int port = 3232;
    Spark.port(port);
    after(
        (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
        });

    try {
      FirebaseInitialize.initialize();
    } catch (Exception e) {
      System.err.println("Could not connect to database: " + e.getMessage());
    }
    Spark.get("registerUser", new RegistrationHandler());
    Spark.get("getMeikById", new GetMeikHandler(imageCacheService));
    Spark.get("getAllMeiks", new GetAllMeikHandler(imageCacheService));
    Spark.get("getUserById", new GetUserHandler());
    Spark.get("updateMeik", new UpdateUserHandler());
    Spark.get("newFirstYear", new CreateFirstYearHandler());
    Spark.get("getRecMeiks",new GetReccsFromTagsHandler());

      Spark.notFound(
        (request, response) -> {
          response.status(404); // Not Found
          return "404 Not Found - The requested endpoint does not exist.";
        });
    Spark.init();
    Spark.awaitInitialization();

    // Notice this link alone leads to a 404... Why is that?
    System.out.println("Server started at http://localhost:" + port);
  }

}
