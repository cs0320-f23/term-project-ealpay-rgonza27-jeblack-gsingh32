package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import spark.Spark;

/**
 * Top Level class for our project, utilizes spark to create and maintain our server. We setup all
 * the endpoint handler in the main method for this class. We have four total endpoints: loadCSV,
 * searchCSV, viewCSV, broadBand (and mock but that is not necessary for actual functionality).
 */
public class Server {


  /**
   * Runs Server.
   *
   * @param args none
   */
  public static void main(String[] args) throws IOException {
    FileInputStream serviceAccount =
        new FileInputStream(
            "/Users/robertogonzales/Desktop/CS0320/term-project-ealpay-rgonza27-jeblack-gsingh32/backend/src/main/java/edu/brown/cs/student/main/server/private/meikdatabase-firebase-adminsdk-5r9bn-be1c95c791.json");

    int port = 3232;
    Spark.port(port);
    after(
        (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
        });

    Spark.get(
        "/",
        (request, response) -> {
          response.type("application/json");
          response.status(200);

          return "Welcome! Here are your options:\n"
              + "\n"
              + "- /broadBand: Used to look at Information from the US Census, "
              + "you must provide a State and County name!\n"
              + "\n"
              + "- /loadCSV: Used to load any CSV file you have!\n"
              + "\n"
              + "- /search: Used to search on the loaded CSV file!\n"
              + "\n"
              + "- /viewCSV: Used to view the CSV file!\n"
              + "\n"
              +
              // Add more options as needed
              "Choose an option and make a request by typing it in the URL!.\n"
              + "\n"
              + "Example -> http://localhost:3232/broadBand?State=California&county=San%20Francisco";
        });

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
