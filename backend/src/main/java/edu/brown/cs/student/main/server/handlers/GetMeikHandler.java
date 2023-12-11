package edu.brown.cs.student.main.server.handlers;

import edu.brown.cs.student.main.User.User;
import edu.brown.cs.student.main.User.UserInformation;
import edu.brown.cs.student.main.server.responses.UserDataResponse;
import edu.brown.cs.student.main.server.responses.MeikDataResponse;
import edu.brown.cs.student.main.utils.FirebaseStorageService;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetMeikHandler implements Route {

  @Override
  public Object handle(Request request, Response response) throws Exception {
    response.type("application/json");
    String meikId = request.queryParams("id");

    // Return the user data as JSON
    return getMeikById(meikId);
  }

  /**
   * This returns meik information from a given uid and serializes it into JSON format.
   * @param meikId
   * @return
   */
  private String getMeikById(String meikId) {
    Map<String, Object> meikResponse = new HashMap<>();

    if (meikId == null) {
      meikResponse.put("err_bad_rqst", "Missing ID");
      MeikDataResponse response = new MeikDataResponse("Null", meikResponse, null, null);
      return response.serialize();
    }

    try {
      UserInformation userInformation = new UserInformation();
      User meik = userInformation.getUser(meikId, "meiks");

      if (meik == null) {
        meikResponse.put("result", "failure");
        meikResponse.put("message", "No user found with id: " + meikId);
        UserDataResponse response = new UserDataResponse(meikId, meikResponse);
        return response.serialize();
      }

      meikResponse.put("user", meik);
      meikResponse.put("result", "success");

      // Get meik image
      FirebaseStorageService storageService = new FirebaseStorageService();
      byte[] image = storageService.getImageBytes("images/" + meikId + ".jpg");
      String imageData = Base64.getEncoder().encodeToString(image);
      MeikDataResponse response = new MeikDataResponse(meikId, meikResponse, imageData, null);

      return response.serialize();

    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      Map<String, Object> errorResponse = new HashMap<>();

      // Create record for failures in retrieving data.
      errorResponse.put("result", "Failure");
      errorResponse.put("message", e.getMessage());
      MeikDataResponse response = new MeikDataResponse(meikId, errorResponse, null, null);

      return response.serialize();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}


