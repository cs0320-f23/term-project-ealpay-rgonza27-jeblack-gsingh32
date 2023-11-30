package edu.brown.cs.student.main.server.handlers;
import edu.brown.cs.student.main.server.responses.RegistrationResponse;

import edu.brown.cs.student.main.Authorization.RegisterUser;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegistrationHandler implements Route {

  /***
   * This handler represents what happens when the end point to register a user is hit.
   * @param request  The request object providing information about the HTTP request
   * @param response The response object providing functionality for modifying the response
   * @return Returns a Registration Response
   * @throws Exception Various exceptions that might occur when registrating
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    String email = request.queryParams("email");
    String password = request.queryParams("password");
    if (email == null) {
      return new RegistrationResponse(
              "Failure", "none", false, "Please provide a brown affiliated email.")
          .serialize();
    }
    if (password == null) {
      return new RegistrationResponse("Failure", "none", false, "Please provide a password.")
          .serialize();
    }
    try {
      RegisterUser registerUser = new RegisterUser();
      boolean result = registerUser.registerUser(email,password);
      return new RegistrationResponse("Success", email, result, "Registration Complete")
          .serialize();
    } catch (Exception e) {
      return new RegistrationResponse("Failure", email, false, e.getMessage()).serialize();
    }
  }
}
