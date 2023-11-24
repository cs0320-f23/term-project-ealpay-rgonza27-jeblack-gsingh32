package edu.brown.cs.student.main.server;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import spark.Request;
import spark.Response;
import spark.Route;

/** endpoint handler for viewCSV. */
public class FireBaseHandler implements Route {

  /**
   * Constructor for the ViewHandler, takes in a shared version of a container object. (Shared
   * between LoadCSV, viewCSV, searchCSV)
   */
  public FireBaseHandler() {}

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
      // Email and password of the existing user
      String email = "user@example.com";
      String password = "password";

      // Sign in with email and password
      FirebaseAuth auth = FirebaseAuth.getInstance();
      FirebaseToken token = auth.verifyIdToken("");

      // Access user information from the token
      String uid = token.getUid();
      System.out.println("User successfully logged in. UID: " + uid);
    } catch (FirebaseAuthException e) {
      // Handle authentication errors
      if ("ERROR_USER_NOT_FOUND".equals(e.getErrorCode())) {
        System.out.println("User not found.");
      } else if ("ERROR_WRONG_PASSWORD".equals(e.getErrorCode())) {
        System.out.println("Incorrect password.");
      } else {
        System.out.println("Authentication failed: " + e.getMessage());
      }
    }
    try {
      response.type("application/json");
      return null;
    } catch (NullPointerException e) {
      return ApiUtilities.toJsonStatus(e.getMessage(), true);
    }
  }
}
